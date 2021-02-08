package basic.ch04_detatch;

import basic.config.EntityManagerGenerator;
import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EX_01_Detach {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        long memberId = 3L;
        String memberName = "최용석";

        try {
            Member member = new Member();
            member.setId(memberId);
            member.setName(memberName);

            entityManager.persist(member);
            Member selectedMember = entityManager.find(Member.class, memberId);
            System.out.println("========== Before Detach");
            System.out.println(" -> persist()를 이용하여 객체의 상태가 영속 상태로, 객체 조회 시 1차 캐시인 영속성 컨텍스트에서 조회 하여 별도의 Select 쿼리 실행 없음");
            System.out.println(selectedMember);

            System.out.println("========== After Detach");
            System.out.println(" -> detach()를 이용하여 객체의 상태가 준영속 상태로, 1차 캐시인 영속성 컨텍스트에서 조회 되지 않아 Select 쿼리 실행 하여 객체 조회");
            entityManager.detach(member);
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
