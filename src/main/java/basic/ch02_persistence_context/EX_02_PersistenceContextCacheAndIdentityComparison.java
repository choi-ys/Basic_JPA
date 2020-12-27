package basic.ch02_persistence_context;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactiory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * EntityManager.persist(Object)를 이용한 동일 트랜잭션 범위에서의 영속성 컨텍스트의 1차 Cache 및 동일성 보장 예제
 */
public class EX_02_PersistenceContextCacheAndIdentityComparison {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            long memberId = 9L;

            Member selectedMember01 = entityManager.find(Member.class, memberId);
            System.out.println("영속 상태 [managed] : Select 쿼리를 실행하여 영속성 컨텍스트에 1차 캐싱 -> "+selectedMember01.toString());

            Member selectedMember02 = entityManager.find(Member.class, memberId);
            System.out.println("영속성 컨텍스트에서 객체 조회 : DB Select 쿼리를 실행하지 않고 영속성 컨텍스트에 캐싱된 내용을 조회" + selectedMember02.toString());

            System.out.println("동일성 비교 (identity comparison) : selectedMember01 == selectedMember02 -> " + (selectedMember01 == selectedMember02));
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        closeEntityManagerFactiory();
    }
}