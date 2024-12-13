package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Tournament;
import hill.ascona.asconapipergames.managers.DAOManager;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private EntityManager em;
    private EntityTransaction transaction;

    public boolean saveTM(Tournament tm){
        initializeEM();
        try{
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(tm);
            transaction.commit();
            return true;
        } catch (Exception e){
            DAOManager.rollback(em, transaction);
            return false;
        } finally {
            em.close();
        }
    }

    public Tournament getTmById(int id){
        em = DAOManager.EMF.createEntityManager();
        Tournament tmnt = em.find(Tournament.class, id);
        em.close();
        return tmnt;
    }

    public List<Tournament> getAllTournaments(){
        initializeEM();
        List<Tournament> tmts = new ArrayList<>();
        TypedQuery<Tournament> query = em.createQuery("FROM Tournament", Tournament.class);
        tmts.addAll(query.getResultList());
        return tmts;
    }

    private void initializeEM(){
        em = DAOManager.EMF.createEntityManager();
        transaction = null;
    }
}
