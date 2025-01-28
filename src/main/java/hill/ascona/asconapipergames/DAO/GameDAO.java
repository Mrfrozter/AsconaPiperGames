package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Match;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.entities.Team;
import hill.ascona.asconapipergames.entities.Tournament;
import jakarta.persistence.*;

import java.util.List;

public class GameDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Skapa (Create) (CRUD)
    public boolean saveGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(game);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) CRUD
    public Game getGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return entityManager.find(Game.class, id);
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) (CRUD) alla spel
    public List<Game> getAllGames() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Game> query = entityManager.createQuery("FROM Game", Game.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) ett spel baserat på titel
    public Game getByName(String title) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g WHERE g.title = :title", Game.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("Game not found with title: " + title);
            return null;
        } finally {
            entityManager.close();
        }
    }

    // Uppdatera (Update) ett spel
    public boolean updateGame(Game gameToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game existingGame = entityManager.find(Game.class, gameToUpdate.getId());
            if (existingGame == null) {
                System.err.println("Game with ID " + gameToUpdate.getId() + " not found.");
                return false;
            }
            entityManager.merge(gameToUpdate);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error updating game: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Ta bort (Delete) ett spel
    public boolean deleteGame(Game gameToDelete) {
        return deleteGameById(gameToDelete.getId());
    }

    // Ta bort (Delete) ett spel baserat på ID
    public boolean deleteGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Hämta spelet som ska raderas
            Game gameToDelete = entityManager.find(Game.class, id);
            if (gameToDelete == null) {
                System.err.println("Game with ID " + id + " not found.");
                return false;
            }

            // Radera kopplingar som andra har till spelet (Team, Person, Match, Tournament)
            detachRelations(gameToDelete, entityManager);

            entityManager.remove(gameToDelete);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting game by ID: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Hjälpmetod för att ta bort kopplingar innan radering
    private void detachRelations(Game gameToDelete, EntityManager entityManager) {
        // Ta bort referenser i Java-objekt
        for (Team t : gameToDelete.getTeams()) {
            t.setGame(null);
        }

        for (Person p : gameToDelete.getPersons()) {
            p.setGame(null);
        }

        for (Match m : gameToDelete.getMatches()) {
            m.setGame(null);
        }

        for (Tournament t : gameToDelete.getTournaments()) {
            t.setGame(null);
        }

        // Ta bort matcher kopplade till spelet
        Query deleteMatchesQuery = entityManager.createQuery(
                "DELETE FROM Match m WHERE m.game.id = :gameId"
        );
        deleteMatchesQuery.setParameter("gameId", gameToDelete.getId());
        deleteMatchesQuery.executeUpdate();

        // Ta bort turneringar kopplade till spelet
        Query deleteTournamentsQuery = entityManager.createQuery(
                "DELETE FROM Tournament t WHERE t.game.id = :gameId"
        );
        deleteTournamentsQuery.setParameter("gameId", gameToDelete.getId());
        deleteTournamentsQuery.executeUpdate();

        // Sätt personernas team-referens till NULL
        Query updatePersonTeamQuery = entityManager.createQuery(
                "UPDATE Person p SET p.team = NULL WHERE p.team IN (SELECT t FROM Team t WHERE t.game.id = :gameId)"
        );
        updatePersonTeamQuery.setParameter("gameId", gameToDelete.getId());
        updatePersonTeamQuery.executeUpdate();

        // Ta bort personer kopplade till spelet
        Query deletePersonsQuery = entityManager.createQuery(
                "DELETE FROM Person p WHERE p.game.id = :gameId"
        );
        deletePersonsQuery.setParameter("gameId", gameToDelete.getId());
        deletePersonsQuery.executeUpdate();

        // Ta bort team kopplade till spelet
        Query deleteTeamsQuery = entityManager.createQuery(
                "DELETE FROM Team t WHERE t.game.id = :gameId"
        );
        deleteTeamsQuery.setParameter("gameId", gameToDelete.getId());
        deleteTeamsQuery.executeUpdate();
    }
}