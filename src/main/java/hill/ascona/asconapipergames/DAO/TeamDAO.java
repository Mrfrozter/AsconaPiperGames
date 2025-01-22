package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.managers.DAOManager;
import jakarta.persistence.*;
import hill.ascona.asconapipergames.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    //CRUD-operationer (create, read, update, delete)

    /*private  static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
    private EntityManager em;
    private EntityTransaction transaction; */

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Tagen från TournamentDAO
    private EntityManager em;
    private EntityTransaction transaction;

    // Create
    public boolean saveTeam(Team team){

        EntityManager entityManager = DAOManager.getEntityManager();
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
        EntityManager entityManager = DAOManager.getEntityManager();
        Team teamToReturn = entityManager.find(Team.class, id);
        entityManager.close();
        return teamToReturn;
    }

    //Read (alla teams)
    public List<Team> getAllTeams(){
        EntityManager entityManager = DAOManager.getEntityManager();
        List<Team> listToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("FROM Team", Team.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    // Update
    public void updateTeam(Team teamToUpdate){
        EntityManager entityManager = DAOManager.getEntityManager();
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
    //Ny deleteteam
    public void deleteTeam(Team team){
        EntityManager entityManager = DAOManager.getEntityManager();
        EntityTransaction transaction = null;
        try {

            transaction = entityManager.getTransaction();
            transaction.begin();

            if(!entityManager.contains(team)){
                team = entityManager.merge(team);
            }

            //Ta bort kopplingar till spelare
            entityManager.createQuery("UPDATE Person p SET p.team = NULL WHERE p.team = :team")
                    .setParameter("team", team)
                    .executeUpdate();

            // Ta bort koppling till spelet
            team.setGame(null);
            entityManager.merge(team);

            // Ta bort teamet
            entityManager.remove(team);

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

    //Gammal deleteteam
    /*public void deleteTeam(Team team){
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
    }*/

    public boolean deleteTeamById(int id){
        EntityManager entityManager = DAOManager.getEntityManager();
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
        EntityManager entityManager = DAOManager.getEntityManager();
        try{
            // JPQL-fråga för att hämta spelet baserat på titeln
            TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE t.teamName = :teamName", Team.class);
            query.setParameter("teamName", teamName);
            return query.getSingleResult(); // Returnera teamet om det hittas
        } catch (NoResultException e) {
            // Returnera null om inget spel hittas
            return null;
        } finally {
            entityManager.close();
        }
    }

//----Elham Farhang--(method getTeamIdByGameId())----
    public List<Team> getTeamIdByGameId(Game game_id) {
        EntityManager entityManager = DAOManager.getEntityManager();
        List<Team> teamInfoToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("SELECT t FROM Team t WHERE t.game = :variable", Team.class);
        result.setParameter("variable", game_id);
        teamInfoToReturn.addAll(result.getResultList());
        entityManager.close();
        return teamInfoToReturn;
    }

    private void initializeEM(){
        em = DAOManager.EMF.createEntityManager();
        transaction = null;
    }

}





