

//----Elham Farhang----

package hill.ascona.asconapipergames.DAO;
import hill.ascona.asconapipergames.entities.Team;
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

    public List<Person> getPlayersInfoByTeamId(List<Team> team) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> playersToReturn = new ArrayList<>();
        for(Team i: team){
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.team = :variable", Person.class);
        result.setParameter("variable", i);
        playersToReturn.addAll(result.getResultList());}
        entityManager.close();
        return playersToReturn;
    }

    public List<Person> getAllPlayersOrUsers(String role) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> listToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.role = :variable", Person.class);
        result.setParameter("variable", role);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    public List<Person> getPersonInfo(String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> personInfoToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.name = :variable", Person.class);
        result.setParameter("variable", name);
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
            TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p WHERE p.nickname = :variable", Person.class);
            query.setParameter("variable", nickname);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
}
