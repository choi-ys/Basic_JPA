package basic.ch06_object_association.ex_03_two_way_association_example;

import basic.ch06_object_association.ex_03_two_way_association_example.domain.entity.EX03_Team;
import basic.ch06_object_association.ex_03_two_way_association_example.domain.entity.EX03_User;
import basic.config.EntityManagerGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/** 용석 : 2021-02-15
 * [#1] 연관된 객체들의 양방향 참조를 위한 양방향 연관관계 설정 예제계
 */
public class EX_03_TwoWayAssociationExample {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //given양
        String teamName = "naver";
        String userName1 = "choi-ys1";
        String userName2 = "choi-ys2";

        try {
            /** Team객체와 User객체의 DB 테이블 저장 */
            EX03_Team team = new EX03_Team();
            team.setTeamName(teamName);
            entityManager.persist(team);

            EX03_User user1 = new EX03_User();
            user1.setUsername(userName1);
            user1.setTeam(team);
            entityManager.persist(user1);

            EX03_User user2 = new EX03_User();
            user2.setUsername(userName2);
            user2.setTeam(team);
            entityManager.persist(user2);

            /** User및 Team정보 조회 쿼리 출력을 위한 flush 및 영속성 컨텍스트 clear */
            entityManager.flush();
            entityManager.clear();

            /** 양방향 연관관계 설정으로 인핸 객체간의 양방향 참조 가능
             * EX03_User -> EX03_Team
             * Ex03_Team -> EX03_User
             */
            EX03_User selectedUser = entityManager.find(EX03_User.class, user1.getUserId());
            System.out.println(selectedUser.getUsername());

            System.out.println("====================");
            List<EX03_User> userList = selectedUser.getTeam().getUserList();
            for (EX03_User user : userList){
                System.out.println("userList : " + user.getUsername());
            }
            System.out.println("====================");

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        EntityManagerGenerator.closeEntityManagerFactory();
    }
}
