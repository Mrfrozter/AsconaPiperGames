package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Game;
import hill.ascona.asconapipergames.entities.Person;
import hill.ascona.asconapipergames.entities.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameDAO {
    // Skapa en EntityManagerFactory för att hantera databasanslutningar
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
            System.out.println(e.getMessage());
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
            // JPQL-fråga för att hämta spelet baserat på titeln
            TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g WHERE g.title = :title", Game.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Returnera null om inget spel hittas
            return null;
        } finally {
            entityManager.close();
        }
    }

    // Uppdatera (Update) ett spel
    public void updateGame(Game gameToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(gameToUpdate);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Ta bort (Delete) ett spel
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
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
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
            if (gameToDelete != null) {
                entityManager.remove(gameToDelete);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

/////////////

    public Game getGameIdByTitle(String title) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Game gameInfoToReturn = null;
        TypedQuery<Game> result = entityManager.createQuery("SELECT g FROM Game g WHERE g.title = :variable", Game.class);
        result.setParameter("variable", title);
        gameInfoToReturn=result.getSingleResult();
        return gameInfoToReturn;
    }
}