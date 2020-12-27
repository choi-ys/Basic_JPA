package basic.ch01_member_crud;

import domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.List;

import static basic.config.EntityManagerGenerator.closeEntityManagerFactiory;
import static basic.config.EntityManagerGenerator.generateEntityManager;

/**
 * 용석 : 2020-12-28 (월)
 * EntityManager의 JPQL을 이용한 Mmember목록 조회
 */
public class EX_05_MemberListRead_With_JPQL {

    public static void main(String[] args) {
        EntityManager entityManager = generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        int offset = 0;
        int limit = 2;

        try {
            List<Member> memberList = entityManager.createQuery("select m from Member as m")
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            for (Member member : memberList){
                System.out.println(member.getId() + " -> " + member.getName());
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
