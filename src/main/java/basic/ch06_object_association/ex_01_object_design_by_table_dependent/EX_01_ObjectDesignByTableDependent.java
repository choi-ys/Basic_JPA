package basic.ch06_object_association.ex_01_object_design_by_table_dependent;


import basic.ch06_object_association.ex_01_object_design_by_table_dependent.domain.entity.EX01_Team;
import basic.ch06_object_association.ex_01_object_design_by_table_dependent.domain.entity.EX01_User;
import basic.config.EntityManagerGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/** 용석 : 2021-02-13
 * [#1] 테이블 모델링 구조에 맞춘 객체 설계
 *  - 객체 설계 시 테이블 컬럼을 필드로 가지도록 설계
 *
 * [#2] 테이블 모델링 구조에 맞춘 객체 설계 예제의 차이점 및 문제점
 *  - 차이점 : 아래와 같은 객체와 테이블의 차이점으로 인해 문제점이 발생할 수 있다.
 *    - 객체는 참조를 통해 연관된 객체에 접근
 *    - 테이블은 외래 키를 통해 조인을 이용하여 연관된 테이블의 데이터를 조회
 *
 *  - 문제점
 *    - 연관된 객체를 필드로 가지는 것이 아닌, 외래키와 매핑된 필드를 가지므로,
 *      연관된 객체의 데이터를 조회하는 경우 외래키와 매핑된 필드를 이용하여 재 조회해야 하는 문제점이 발생.
 *    - 결론 : 테이블 모델링 구조에 맞추어 객체를 설계할 경우, 테이블의 외래키와 매핑된 필드로는 연관 객체를 참조할 수 없다.
 */
public class EX_01_ObjectDesignByTableDependent {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //given
        String teamName = "naver";
        String userName = "choi-ys";

        try {
            /** Team객체와 User객체의 DB 테이블 저장 */
            EX01_Team ex01Team = new EX01_Team();
            ex01Team.setTeamName(teamName);
            entityManager.persist(ex01Team);

            EX01_User user = new EX01_User();
            /** 문제점 1 : 연관 객체를 다루는 것이 아닌 외래키와 매핑된 필드를 직접 다룸 */
            user.setTeamId(ex01Team.getTeamId());
            user.setUsername(userName);
            entityManager.persist(user);

            /** User및 Team정보 조회 쿼리 출력을 위한 flush 및 영속성 컨텍스트 clear */
            entityManager.flush();
            entityManager.clear();

            /** User정보와 User가 속한 Team정보 조회 */
            EX01_User selectedUser = entityManager.find(EX01_User.class, user.getUserId());
            System.out.println("selectedUser : " + selectedUser);
            /** 문제점 2 :
             * User객체와 User가 속한 Team 정보를 조회 하기 위해
             * User객체 내 필드로 선언된 Team객체의 외래키와 매핑된 필드를 이용하여 Team객체를 조회한다.
             * */
            EX01_Team selectedTeam = entityManager.find(EX01_Team.class, user.getTeamId());
            System.out.println("selectedTeam : " + selectedTeam);

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
