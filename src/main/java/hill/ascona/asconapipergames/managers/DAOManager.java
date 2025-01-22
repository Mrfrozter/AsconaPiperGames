package hill.ascona.asconapipergames.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAOManager {
        public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("myconfig");

        // ny från gpt
        public static EntityManager getEntityManager() {
            return EMF.createEntityManager();
        }

        public static void rollback(EntityManager entityManager, EntityTransaction transaction){
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }
}
