package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Person;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Skapa (Create) en ny person
    public boolean addPerson(Person person) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(person);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error adding person: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) en person baserat på ID
    public Person getPersonById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return entityManager.find(Person.class, id);
        } catch (Exception e) {
            System.err.println("Error fetching person by ID: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) alla personer
    public List<Person> getAllPersons() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Person> query = entityManager.createQuery("FROM Person", Person.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error fetching all persons: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            entityManager.close();
        }
    }

    // Läs (Read) alla personer baserat på roll
    public List<Person> getAllPlayersOrUsers(String role) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Person> query = entityManager.createQuery("FROM Person p WHERE p.role = :role", Person.class);
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error fetching persons by role: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            entityManager.close();
        }
    }

    // Uppdatera (Update) en persons information
    public boolean updatePerson(Person personToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Person existingPerson = entityManager.find(Person.class, personToUpdate.getId());
            if (existingPerson == null) {
                System.err.println("Person with ID " + personToUpdate.getId() + " not found.");
                return false;
            }
            entityManager.merge(personToUpdate);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error updating person: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Ta bort (Delete) en person
    public boolean deletePerson(Person personToDelete) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(personToDelete)) {
                personToDelete = entityManager.merge(personToDelete);
            }
            entityManager.remove(personToDelete);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting person: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Ta bort (Delete) en person baserat på ID
    public boolean deletePersonById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Person personToDelete = entityManager.find(Person.class, id);
            if (personToDelete == null) {
                System.err.println("Person with ID " + id + " not found.");
                return false;
            }
            entityManager.remove(personToDelete);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting person by ID: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // Hämta person baserat på nickname
    public Person getByNickname(String nickname) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p WHERE p.nickname = :nickname", Person.class);
            query.setParameter("nickname", nickname);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("Person with nickname " + nickname + " not found.");
            return null;
        } finally {
            entityManager.close();
        }
    }

    // Uppdatera game-referenser till NULL för en person
    public boolean clearGameReferences(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createQuery("UPDATE Person p SET p.game = NULL WHERE p.game.id = :gameId");
            query.setParameter("gameId", gameId);
            int updatedRows = query.executeUpdate();
            transaction.commit();
            System.out.println("Cleared game references for " + updatedRows + " persons.");
            return true;
        } catch (Exception e) {
            System.err.println("Error clearing game references: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}