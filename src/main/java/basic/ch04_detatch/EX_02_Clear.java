package basic.ch04_detatch;

import basic.config.EntityManagerGenerator;
import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/** detach : 준영속 상태
 *
 */
public class EX_02_Clear {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        long memberId = 99L;
        String memberName = "최용석";

        try {
            Member member = new Member();
            member.setId(memberId);
            member.setName(memberName);

            System.out.println("========== Add Member Entity to Persist Context ==========");
            entityManager.persist(member);
            Member selectedMember = entityManager.find(Member.class, memberId);
            System.out.println(selectedMember);

            //EX)01 : entityManager.detach({entity})를 이용한 특정 엔티티의 상태를 준영속 상태로 변경
//            System.out.println("========== detach Member Entity from Persist Context ==========");
//            entityManager.detach(member);

            //EX)02 : entityManager.clear()를 이용한 모든 엔티티의 상태를 준영속 상태로 변경
            System.out.println("========== clear All Entity from Persist Context ==========");
            entityManager.clear();
            selectedMember = entityManager.find(Member.class, memberId);
            System.out.println(selectedMember);

            //EX)03 : entityManager.close()를 이용한 모든 엔티티의 상태를 준영속 상태로 변경
            System.out.println("========== close Entity Manager ==========");
            entityManager.close();

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
