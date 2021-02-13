package basic.ch03_flush;

import basic.config.EntityManagerGenerator;
import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;

/**
 * 용석 : 2021-02-08 (월)
 * Flush Mode Option : EntityManager의 setFlushMode()를 이용하여 Flush Mode Option을 설정할 수 있다.
 *  - FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시 (기본값이며 권장사항)
 *  - FlushModeType.COMMIT : 커밋할때만 플러시
 */
public class EX_02_FlushModeOption {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        long memberId = 2L;
        String memberName = "최용석";

        try {
            Member member = new Member();
            member.setId(memberId);
            member.setName(memberName);

            entityManager.persist(member);

            System.out.println("========== Start Flush ==========");
            entityManager.setFlushMode(FlushModeType.COMMIT);
            entityManager.flush();
            System.out.println("========== End Flush ==========");
            Member selectedMember = entityManager.find(Member.class, memberId);
            System.out.println(selectedMember);

            entityTransaction.commit();
            selectedMember = entityManager.find(Member.class, memberId);
            System.out.println(selectedMember);

        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        EntityManagerGenerator.closeEntityManagerFactory();
    }
}

