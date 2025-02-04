package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Tournament;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TournamentDAO {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("myconfig");
    private EntityManager em;
    private EntityTransaction transaction;

    //    C
    public boolean saveTM(Tournament tm) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(tm);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("WTF\n" + e.getMessage());
            rollback();
            return false;
        } finally {
            em.close();
        }
    }

    //    R
    public Tournament getTmById(int id) {
        rollback();
        Tournament tmnt = em.find(Tournament.class, id);
        em.close();
        return tmnt;
    }

    public List<Tournament> filterByString(String filter, String search) {
        em = EMF.createEntityManager();
        try {
            TypedQuery<Tournament> tmnts = em.createQuery(String.format("SELECT t FROM Tournament t WHERE t.%s LIKE :search", filter), Tournament.class);
            tmnts.setParameter("search", "%"+search+"%");
            return tmnts.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Tournament> filterByGame(Game game) {
        em = EMF.createEntityManager();
        try {
            TypedQuery<Tournament> tmnts = em.createQuery("SELECT t FROM Tournament t WHERE t.game = :search", Tournament.class);
            tmnts.setParameter("search", game);
            return tmnts.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Tournament> getAllTournaments() {
        em = EMF.createEntityManager();
        List<Tournament> tmts = new ArrayList<>();
        TypedQuery<Tournament> query = em.createQuery("FROM Tournament", Tournament.class);
        tmts.addAll(query.getResultList());
        return tmts;
    }

    //    U
    public void updateTmnt(Tournament tmnt) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();

            if (em.contains(tmnt))
                em.persist(tmnt);
            em.merge(tmnt);
            transaction.commit();

        } catch (Exception e) {
            rollback();
        } finally {
            em.close();
        }
    }

    //    D
    public boolean deleteTmnt(Tournament tmnt) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            if (!em.contains(tmnt)) {
                tmnt = em.merge(tmnt);
            }
            em.remove(tmnt);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to delete: " + e.getMessage());
            rollback();
        } finally {
            em.close();
        }
        return false;
    }

    private void initializeEM() {
        em = EMF.createEntityManager();
        transaction = null;
    }

    private void rollback() {
        if (em != null && transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
