package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Tournament;
import hill.ascona.asconapipergames.managers.DAOManager;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private EntityManager em;
    private EntityTransaction transaction;

    // Create (Save a Tournament)
    public boolean saveTM(Tournament tm) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(tm);
            transaction.commit();
            return true;
        } catch (Exception e) {
            DAOManager.rollback(em, transaction);
            return false;
        } finally {
            em.close();
        }
    }

    // Read (Get a Tournament by ID)
    public Tournament getTmById(int id) {
        em = DAOManager.EMF.createEntityManager();
        try {
            return em.find(Tournament.class, id);
        } finally {
            em.close();
        }
    }

    // Read (Get All Tournaments)
    public List<Tournament> getAllTournaments() {
        initializeEM();
        try {
            TypedQuery<Tournament> query = em.createQuery("FROM Tournament", Tournament.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Read (Get Tournament by Title)
    public Tournament getTournamentByTitle(String title) {
        initializeEM();
        try {
            TypedQuery<Tournament> query = em.createQuery("SELECT t FROM Tournament t WHERE t.title = :title", Tournament.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("Tournament not found with title: " + title);
            return null;
        } finally {
            em.close();
        }
    }

    // Update (Modify an Existing Tournament)
    public boolean updateTournament(Tournament tournamentToUpdate) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Tournament existingTournament = em.find(Tournament.class, tournamentToUpdate.getId());
            if (existingTournament == null) {
                System.err.println("Tournament with ID " + tournamentToUpdate.getId() + " not found.");
                return false;
            }
            em.merge(tournamentToUpdate);
            transaction.commit();
            return true;
        } catch (Exception e) {
            DAOManager.rollback(em, transaction);
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Delete (Remove a Tournament)
    public boolean deleteTM(Tournament tm) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Tournament managedTM = em.contains(tm) ? tm : em.merge(tm);
            em.remove(managedTM);
            transaction.commit();
            return true;
        } catch (Exception e) {
            DAOManager.rollback(em, transaction);
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Delete by ID
    public boolean deleteTournamentById(int id) {
        initializeEM();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Tournament tmToDelete = em.find(Tournament.class, id);
            if (tmToDelete == null) {
                System.err.println("Tournament with ID " + id + " not found.");
                return false;
            }
            em.remove(tmToDelete);
            transaction.commit();
            return true;
        } catch (Exception e) {
            DAOManager.rollback(em, transaction);
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    private void initializeEM() {
        em = DAOManager.EMF.createEntityManager();
        transaction = null;
    }
}
