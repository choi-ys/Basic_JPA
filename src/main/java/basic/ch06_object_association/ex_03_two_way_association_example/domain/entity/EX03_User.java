package basic.ch06_object_association.ex_03_two_way_association_example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "ch_06_ex_03_user_tb")
@Getter @Setter @NoArgsConstructor
//@ToString
public class EX03_User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, length = 25)
    private String username;

    @ManyToOne // [user:team = N:1]인 현재 객체와 필드로 선언된 객체와의 관계를 명시
    @JoinColumn(name = "team_id") // 현재 객체와 필드로 선언된 객체와의 관계상 Join해야되는 Column을 명시
    private EX03_Team team;
}
