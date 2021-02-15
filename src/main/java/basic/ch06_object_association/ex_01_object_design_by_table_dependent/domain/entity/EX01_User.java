package basic.ch06_object_association.ex_01_object_design_by_table_dependent.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "ch_06_ex_01_user_tb")
@Getter @Setter @NoArgsConstructor
@ToString
public class EX01_User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, length = 25)
    private String username;

    @Column(name = "team_id")
    private Long teamId;
}
