package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import jakarta.persistence.*;
import hill.ascona.asconapipergames.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    //CRUD-operationer (create, read, update, delete)

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create
    public boolean saveTeam(Team team){

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(team);
            transaction.commit();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }

    }

    //Read
    public Team getTeamById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Team teamToReturn = entityManager.find(Team.class, id);
        entityManager.close();
        return teamToReturn;
    }

    //Read (alla teams)
    public List<Team> getAllTeams(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> listToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("FROM Team", Team.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    // Update
    public void updateTeam(Team teamToUpdate){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entityManager.contains(teamToUpdate) ? teamToUpdate : entityManager.merge(teamToUpdate));
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

    public void deleteTeam(Team team){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(!entityManager.contains(team)){
                team = entityManager.merge(team);
            }
            entityManager.remove(team);

            // Ett annat sätt att skriva det på:
//            entityManager.remove(entityManager.contains(team) ? car : entityManager.merge(team));

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
    public boolean deleteTeamById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Team teamToDelete = entityManager.find(Team.class, id);
            entityManager.remove(entityManager.contains(teamToDelete) ? teamToDelete : entityManager.merge(teamToDelete));
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

    //Modifierad getByName från GameDAO
    public Team getTeamByName(String teamName){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try{
            // JPQL-fråga för att hämta spelet baserat på titeln
            TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE t.team_name = :teamName", Team.class);
            query.setParameter("teamName", teamName);
            return query.getSingleResult(); // Return the team if found
        } catch (NoResultException e) {
            // Returnera null om inget spel hittas
            return null;
        } finally {
            entityManager.close(); // Ensure entity manager is closed
        }
    }

    //----Elham Farhang--(method getTeamIdByGameId())----
    public List<Team> getTeamIdByGameId(Game game_id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> teamInfoToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("SELECT t FROM Team t WHERE t.game = :variable", Team.class);
        result.setParameter("variable", game_id);
        teamInfoToReturn.addAll(result.getResultList());
        entityManager.close();
        return teamInfoToReturn;
    }
}





