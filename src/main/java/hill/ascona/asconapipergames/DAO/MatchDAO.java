package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import jakarta.persistence.*;
import hill.ascona.asconapipergames.entities.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    // CRUD-operations

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create
    public boolean saveMatch(Match match) {

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }finally {
            entityManager.close();
        }

    }
    // Read One/All
    public Match getMatchById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Match matchToReturn = entityManager.find(Match.class, id);
        entityManager.close();
        return matchToReturn;
    }
/*    List<Match> listToReturn = new ArrayList<>();
    TypedQuery<Match> result = entityManager.createQuery("FROM Match", Match.class);
        listToReturn.addAll(result.getResultList());*/
    /*public List<Match> getAllMatches(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            List<Match> listToReturn = new ArrayList<>(entityManager.createQuery("from Match", Match.class).getResultList());
            transaction.commit();
            return listToReturn;
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
          e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return null;
    }*/

    public List<Match> getAllMatches() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Match> query = entityManager.createQuery("FROM Match ", Match.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }




    // Update
    public void updateMatch(Match matchToUpdate){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entityManager.contains(matchToUpdate) ? matchToUpdate : entityManager.merge(matchToUpdate));
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }


    // Delete
    public void deleteMatch(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(!entityManager.contains(match)){
                match = entityManager.merge(match);
            }
            entityManager.remove(match);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }

    public boolean deleteMatchById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Match matchToDelete = entityManager.find(Match.class, id);
            entityManager.remove(entityManager.contains(matchToDelete) ? matchToDelete : entityManager.merge(matchToDelete));
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }
        finally {
            entityManager.close();
        }
    }
}

