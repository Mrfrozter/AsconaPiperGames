package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Tournament;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myconfig");
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

    public List<Tournament> getAllTournaments() {
//        initializeEM();
        em = emf.createEntityManager();
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
        em = emf.createEntityManager();
        transaction = null;
    }

    private void rollback() {
        if (em != null && transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
