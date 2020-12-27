package basic.ch01_member_crud;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactiory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * EntityManager를 이용한 Mmember객체 조회
 */
public class MemberRead {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        long memberId = 4L;

        try {
            Member member = entityManager.find(Member.class, memberId);
            if(member != null){
                System.out.println(member.getId() + " -> " + member.getName());
            }else{
                System.out.println("404 NotFound -> memberId : " + memberId);
            }

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
