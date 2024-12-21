package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import jakarta.persistence.*;

import java.util.List;

public class GameDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Skapa (Create)
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

    // Läs (Read) ett spel baserat på ID
    public Game getGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return entityManager.find(Game.class, id);
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) alla spel
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
    public boolean deleteGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(game)) {
                game = entityManager.merge(game);
            }
            entityManager.remove(game);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting game: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Ta bort (Delete) ett spel baserat på ID
    public boolean deleteGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToDelete = entityManager.find(Game.class, id);
            if (gameToDelete == null) {
                System.err.println("Game with ID " + id + " not found.");
                return false;
            }
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
}