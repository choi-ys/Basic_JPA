package basic.ch03_flush;

import basic.config.EntityManagerGenerator;
import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * 용석 : 2021-02-08 (월)
 * Flush : 영속성 컨텍스트의 변경내용을 (쓰기 지연 SQL 저장소 에 저장된 SQL) DB에 전송
 *  - 발생 시점
 *    - 직접 호출로 인한 발생
 *    - DB Transaction의 Commit 시점에 발생
 *  - Flush 발생 시
 *    - 1. 변경 감지 발생
 *    - 2. 수정된 Entity를 쓰기 지연 SQL 저장소에 등록
 *    - 3. 쓰기 지연 저장소의 쿼리를 DB에 전송
 *  - Flush 호출 방법
 *    - 직접호출 : EntityManager.flush()
 *    - 자동 호출
 *      - entityTransaction.commit()
 *      - JPQL 쿼리 실행 시
 *  - 주의 사항
 *     - Flush가 발생하여 DB에 전송된 SQL들은 Transaction Commit시에만 실행 된다.
 *     - 운영 환경에서의 Flush 직접 호출은 권장하지 않으며, Test환경에서 수행 쿼리 확인 등의 목적으로만 사용을 권장한다.
 *     - Flush발생 시 영속성 컨텍스트를 비우지 않으며, 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화 한다.
 *     - 트랜잭션이라는 작업단위가 중요하므로, commit 직전에만 동기화 하면 된다.
 */
public class EX_01_Flush {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        long memberId = 1L;
        String memberName = "최용석";

        try {
            Member member = new Member();
            member.setId(memberId);
            member.setName(memberName);

            System.out.println("========== Start Persist ==========");
            entityManager.persist(member);
            System.out.println("========== End Persist ==========");

            System.out.println("========== Start Flush ==========");
            entityManager.flush();
            System.out.println("========== End Flush ==========");

            Member selectedMember = entityManager.find(Member.class, memberId);
            System.out.println(selectedMember);

            System.out.println("========== Start Commit ==========");
            entityTransaction.commit();
            System.out.println("========== End Commit ==========");

            selectedMember = entityManager.find(Member.class, memberId);
            System.out.println(selectedMember);

        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        EntityManagerGenerator.closeEntityManagerFactiory();
    }
}
