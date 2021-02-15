package basic.ch06_object_association.ex_02_one_way_association_example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/** 연관관계 설정
 * 회원 테이블은 외래키인 team_id컬럼을 통해 팀 테이블과 연관관게를 가지므로,
 * 회원(EX02_User)객체 설계 시 외래키인 team_id컬럼을 필드로 가지는 것이 아닌
 * 팀(EX02_Team)객체를 필드로 가지도록 설계하여 연관관계를 설정
 *
 *  - @ManyToOne 어노테이션을 이용해 [user:team = N:1]인 현재 객체와 필드로 선언된 객체와의 관계를 명시
 *  - @JoinColumn 어노테이션을 이용해 현재 객체와 필드로 선언된 객체와의 관계상 조인 해야되는 외래키 컬럼을 명시
 *    - name속성 : 매핑할 외래 키 이름
 */
@Entity @Table(name = "ch_06_ex_02_user_tb")
@Getter @Setter @NoArgsConstructor
@ToString
public class EX02_User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, length = 25)
    private String username;

    @ManyToOne // 객체 연관관계 설정 : [user:team = N:1]인 현재 객체와 필드로 선언된 객체와의 관계를 명시
    @JoinColumn(name = "team_id") // 현재 객체와 필드로 선언된 객체와의 관계상 Join해야되는 Column을 명시
    private EX02_Team team;
}
