package basic.ch06_object_association.ex_03_two_way_association_example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "ch_06_ex_03_team_tb")
@Getter @Setter @NoArgsConstructor
//@ToString
public class EX03_Team {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false, length = 30)
    private String teamName;

    /** 객체의 양방향 연관관계 설정
     * 주의점 :
     *  - 양방향 연관관계 설정 시, 양쪽 모두 같은 외래키를 참조하여 수정하거나 접근하게 되어 데이터 일관성이 깨질 수 있으므로,
     *    반드시 연관관계의 주인을 설정하여, 다른 쪽에서는 읽기 전용으로 사용할 수 있도록 해야한다.(mappedBy를 설정하는 이유)
     *  - 양방향 연관관계가 설정된 객체의 값을 삽입/갱신 하는 경우 반드시 연관관계의 주인 객체에 값을 입력한다.
     *    (mappedBy는 읽기전용 속성이므로, JPA를 이용한 삽입/갱신 대상에서 제외된다.)
     *  - 하지만 연관관계의 주인 객체에만 값을 입력할 경우, 영속성 컨텍스트에는 주인이 아닌 객체에 값은 NULL이나 실제 DB NULL이 아닌 경우가
     *    발생할 수 있으므로, 순수한 객체 관계를 고려하면, 항상 양쪽다 값을 입력해야 한다.
     *
     * 권장사항 :
     *  - 외래키를 가지는 테이블을 모델링한 객체를 연관관계의 주인으로 모델링한다.
     *    - EX) User 테이블이 team_id를 외래키로 가지는 경우, User 테이블을 모델링한 User객체를 연관관계의 주인으로 설정한다.
     *  - *ToOne의 어노테이션을 기술하는 객체를 연관관계의 주인으로 설정한다.
     *  - 연관관계 편의 메소드를 생성하자
     *  - 양방향 매핑시 무한루프를 조심하자
     *    - EX) lombok의 toString(), JSON생성 라이브러리(Entity를 직접 Controller에서 응답하는 경우 발생)
     * @OneToMany : [team:user = 1:N]인 현재 객체와 필드로 선언된 객체와의 관계를 명시
     * mappedBy : 현재 객체를 참조하는 객체 내 선언된 필드명 -> EX03_USER Class의 team필드명
     *  - mappedBy속성이 적용된 필드는 현재 객체를 참조하는 객체 내에서 외래키와 매핑 관계를 가지고 있으므로 읽기 전용
     */
    @OneToMany(mappedBy = "team")
    private List<EX03_User> userList = new ArrayList<>(); // NPE방지 및 기타 이유로 JPA 관례상 초기화
}

