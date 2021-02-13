package basic.ch02_persistence_context;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * EntityManager.persist(Object)를 이용한 영속성 컨텍스트의 트랜잭션을 지원하는 쓰기 지연 (transactional wirte-behind) 예제
 */
public class EX_03_PersistenceContextTransactionalWriteBehind {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            Member member01 = Member.builder().id(10L).name("choi-ys_01").build();
            Member member02 = Member.builder().id(11L).name("choi-ys_02").build();
            System.out.println("영속성 컨텍스트에 할당 전인 비영속 상태 객체 생성");
            System.out.println("member01 -> " + member01.toString());
            System.out.println("member02 -> " + member02.toString());

            entityManager.persist(member01);
            entityManager.persist(member02);
            System.out.println("EntityManager.persist()를 이용하여 영속성 컨텍스트에 객체 저장 및 Insert SQL을 생성하여 쓰기 지연 저장소에 생성");

            entityTransaction.commit();
            System.out.println("EntityTransaction.commit()을 이용하여 쓰지 지연 저장소에 생성된 Insert SQL을 실행");
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        closeEntityManagerFactory();
    }
}
