package hill.ascona.asconapipergames.DAO;

import jakarta.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import hill.ascona.asconapipergames.entities.Game;
import java.util.ArrayList;
import java.util.List;



public class GameDAO {
    // EntityManagerFactory för att hantera databasen
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create
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
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Read One
    public Game getGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Game game = entityManager.find(Game.class, id);
        entityManager.close();
        return game;
    }

    // Read All
    public List<Game> getAllGames() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Game> games = new ArrayList<>();
        TypedQuery<Game> query = entityManager.createQuery("FROM Game", Game.class);
        games.addAll(query.getResultList());
        entityManager.close();
        return games;
    }

    // Update
    public void updateGame(Game gameToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entityManager.contains(gameToUpdate) ? gameToUpdate : entityManager.merge(gameToUpdate));
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Delete
    public void deleteGame(Game game) {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Delete By ID
    public boolean deleteGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToDelete = entityManager.find(Game.class, id);
            if (gameToDelete != null) {
                entityManager.remove(entityManager.contains(gameToDelete) ? gameToDelete : entityManager.merge(gameToDelete));
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}
