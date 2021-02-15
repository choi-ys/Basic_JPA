package basic.ch06_object_association.ex_02_one_way_association_example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity @Table(name = "ch_06_ex_02_team_tb")
@Getter @Setter @NoArgsConstructor
@ToString
public class EX02_Team {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false, length = 30)
    private String teamName;
}

