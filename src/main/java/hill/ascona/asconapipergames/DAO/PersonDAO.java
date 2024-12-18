package hill.ascona.asconapipergames.DAO;

import jakarta.persistence.*;
import hill.ascona.asconapipergames.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public boolean addPerson(Person person){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction  = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(person);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if( entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }finally {
            entityManager.close();
        }
    }

    public List<Person> getPlayersInfoByTeamId(String teamId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> playersToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.teamID = :variabel", Person.class);
        result.setParameter("variabel", teamId);
        playersToReturn.addAll(result.getResultList());
        entityManager.close();
        return playersToReturn;
    }
    public List<Person> getAllPlayersOrUsers(String role ){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> listToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.role = :variabel", Person.class);
        result.setParameter("variabel", role);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    public List<Person> getPersonInfo(String name){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> personInfoToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.name = :variabel", Person.class);
        result.setParameter("variabel", name);
        personInfoToReturn.addAll(result.getResultList());
        return personInfoToReturn;
    }
}
