package basic.ch02_persistence_context;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * JPA PersistenceContext의 변경감지 (Dirty Checking) 예제
 */
public class EX_04_PersistenceContextDirtyChecking {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            Member member = entityManager.find(Member.class, 11L);
            System.out.println("조건에 해당하는 객체를 DB에서 조회 : " + member.toString());

            member.setName("changed named");
            System.out.println("조회한 객체의 필드 중 일부를 변경 : " + member.toString());

            entityTransaction.commit();
            System.out.println("변경 사항을 Commit 하는 시점에 영속성 컨텍스트에 최초에 캐싱된 스냅샷과 Commit 시점의 값을 비교");
            System.out.println("객체의 스냅샷과 Commit 시점의 객체의 값이 다른경우 Update SQL을 쓰기 지연 저장소에 생성 및 SQL 실행");
            System.out.println("Commit 이후 변경 된 member 객체의 값 : " + member.toString());
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        closeEntityManagerFactory();
    }
}
