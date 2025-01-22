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

    // Delete team (den nya)
    public void deleteTeam(Team team) {
        EntityManager entityManager = DAOManager.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Hämta en managed version av teamet med alla dess kopplingar
            Team managedTeam = entityManager.find(Team.class, team.getTeamId());
            if (managedTeam != null) {
                // Hämta alla personer kopplade till detta team
                TypedQuery<Person> query = entityManager.createQuery(
                        "SELECT p FROM Person p WHERE p.team = :team", Person.class);
                query.setParameter("team", managedTeam);
                List<Person> members = query.getResultList();

                // Ta bort team-kopplingen för varje person
                for (Person member : members) {
                    member.setTeam(null);
                    entityManager.merge(member);
                }

                //tillagd egen sådära
                team.setGame(null);

                // Nu kan vi säkert ta bort teamet
                entityManager.remove(managedTeam);
                transaction.commit();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public void remove(Team team) {
        EntityManager entityManager = DAOManager.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(team)) {
                team = entityManager.merge(team);
                entityManager.remove(team);
                transaction.commit();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Gammal deleteTeam
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
    } */

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
        EntityManager entityManager = DAOManager.getEntityManager();
        List<Team> teamInfoToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("SELECT t FROM Team t WHERE t.game = :variable", Team.class);
        result.setParameter("variable", game_id);
        teamInfoToReturn.addAll(result.getResultList());
        entityManager.close();
        return teamInfoToReturn;
    }
}





