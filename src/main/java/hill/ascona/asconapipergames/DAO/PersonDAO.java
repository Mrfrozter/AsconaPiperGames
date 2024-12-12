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

    public Person getPersonInfoById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Person personInfoToReturn = entityManager.find(Person.class, id);
        entityManager.close();
        return personInfoToReturn;
    }
    public List<Person> getAllPersonsInfo(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> listToReturn = new ArrayList<>();
        TypedQuery<Person> result = entityManager.createQuery("FROM Person p WHERE p.role = :variabel", Person.class);
        result.setParameter("variabel", "spelare");
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

}
