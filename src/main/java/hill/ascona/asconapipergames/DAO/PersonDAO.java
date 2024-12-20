package hill.ascona.asconapipergames.DAO;

import jakarta.persistence.*;
import hill.ascona.asconapipergames.entities.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

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
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public List<Person> getPlayersInfoByTeamId(String teamId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> playersToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.team = :variabel", Person.class);
        result.setParameter("variabel", teamId);
        playersToReturn.addAll(result.getResultList());
        entityManager.close();
        return playersToReturn;
    }

    public List<Person> getAllPlayersOrUsers(String role) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> listToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.role = :variabel", Person.class);
        result.setParameter("variabel", role);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    public List<Person> getPersonInfo(String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> personInfoToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.name = :variabel", Person.class);
        result.setParameter("variabel", name);
        personInfoToReturn.addAll(result.getResultList());
        return personInfoToReturn;
    }

    public void updatePlayersInfo(Person dataToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(dataToUpdate)){
                entityManager.persist(dataToUpdate);
            }
//            else {
//                Person player = entityManager.merge(dataToUpdate);
//            }
            entityManager.merge(dataToUpdate);
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


    public void remove(Person player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(player)) {
                player = entityManager.merge(player);
                entityManager.remove(player);
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

    public Person getByNickname(String nickname) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            // JPQL-fråga för att hämta spelet baserat på titeln
            TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p WHERE p.nickname = :variabel", Person.class);
            query.setParameter("variabel", nickname);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Returnera null om inget spel hittas
            return null;
        } finally {
            entityManager.close();
        }
    }
}
