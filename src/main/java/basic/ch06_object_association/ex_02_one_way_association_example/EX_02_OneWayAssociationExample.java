package basic.ch06_object_association.ex_02_one_way_association_example;

import basic.ch06_object_association.ex_02_one_way_association_example.domain.entity.EX02_Team;
import basic.ch06_object_association.ex_02_one_way_association_example.domain.entity.EX02_User;
import basic.config.EntityManagerGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/** 용석 : 2021-02-15
 * [#1] 테이블 모델링 구조에 맞춘 객체 설계의 문제점 해결을 위한 단방향 연관관계 설정 예제
 *  - 객체 설계 시 외래키와 매핑된 연관된 객체를 필드로 가지도록 설계
 *
 * [#2] 단방향 연관관계 매핑으로 인한 테이블 모델링 구조에 맞춘 객체 설계의 문제점 해결
 *   - 외래키와 매핑된 필드가 아닌 외래키와 연관된 객체를 필드로 가지므로 연관 객체 조회 시 재 조회해야 하는 문제점 해결
 */
public class EX_02_OneWayAssociationExample {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //given
        String teamName = "naver";
        String userName = "choi-ys";

        try {
            /** Team객체와 User객체의 DB 테이블 저장 */
            EX02_Team team = new EX02_Team();
            team.setTeamName(teamName);
            entityManager.persist(team);

            EX02_User user = new EX02_User();
            user.setTeam(team); // 단방향 연관관계 설정, 참조 저장
            user.setUsername(userName);
            /** Team객체를 필드로 가진 User객체 저장 시 실행 쿼리
             *  insert into
             *      ch_07_ex_02_user_tb
             *      (team_id, username, user_id)
             *  values
             *      (?, ?, ?)
             */
            entityManager.persist(user);

            /** User및 Team정보 조회 쿼리 출력을 위한 flush 및 영속성 컨텍스트 clear */
            entityManager.flush();
            entityManager.clear();

            /** User정보와 User가 속한 Team정보 조회
             *     select
             *         ex02_user0_.user_id as user_id1_3_0_,
             *         ex02_user0_.team_id as team_id3_3_0_,
             *         ex02_user0_.username as username2_3_0_,
             *         ex02_team1_.team_id as team_id1_2_1_,
             *         ex02_team1_.team_name as team_nam2_2_1_
             *     from
             *         ch_07_ex_02_user_tb ex02_user0_
             *     left outer join
             *         ch_07_ex_02_team_tb ex02_team1_
             *             on ex02_user0_.team_id=ex02_team1_.team_id
             *     where
             *         ex02_user0_.user_id=?
             * */
            EX02_User selectedUser = entityManager.find(EX02_User.class, user.getUserId());
            System.out.println(selectedUser);

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
