package basic.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 용석 : 2020-12-28 (월)
 * 싱글톤을 이용한 EntityManagerFactory 생성
 */
public class EntityManagerGenerator {

    private static EntityManagerFactory entityManagerFactory = null;

    public EntityManagerGenerator() {
    }

    private void createEntityManagerFactory(){

    }

    public static EntityManager generateEntityManager(){
        if(entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("jpa-basic");
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactiory(){
        entityManagerFactory.close();
    }

}
