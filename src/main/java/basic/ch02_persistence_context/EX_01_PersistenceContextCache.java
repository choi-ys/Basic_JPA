package basic.ch02_persistence_context;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * EntityManager.persist(Object)를 이용한 동일 트랜잭션 범위에서의 영속성 컨텍스트의 1차 Cache 예제
 */
public class EX_01_PersistenceContextCache {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            long memberId = 9L;
            String memberName = "choi-ys";
            Member member = Member.builder()
                    .id(memberId)
                    .name(memberName)
                    .build();
            System.out.println("비영속 상태 [new/transient] : 객체 생성 -> " + member.toString());

            entityManager.persist(member);
            System.out.println("영속 상태 [managed] : 영속성 컨텍스트에 저장 -> Insert 쿼리 실행 전");

            Member selectedMember = entityManager.find(Member.class, memberId);
            System.out.println("영속성 컨텍스트에서 객체 조회 : DB Select 쿼리를 실행하지 않고 영속성 컨텍스트에 캐싱된 내용을 조회 -> " + selectedMember.toString());

            entityTransaction.commit();
            System.out.println("영속 상태 [managed] : Insert 쿼리 실행 및 DB Commit");
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        closeEntityManagerFactory();
    }
}