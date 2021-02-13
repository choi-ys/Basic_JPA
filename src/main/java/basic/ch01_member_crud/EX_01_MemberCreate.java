package basic.ch01_member_crud;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * EntityManager를 이용한 Mmember객체 저장
 */
public class EX_01_MemberCreate {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        long memberId = 4L;
        String memberName = "choi-ys";

        try {
            Member member = new Member();
            member.setId(memberId);
            member.setName(memberName);

            entityManager.persist(member);

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        closeEntityManagerFactory();
    }
}
