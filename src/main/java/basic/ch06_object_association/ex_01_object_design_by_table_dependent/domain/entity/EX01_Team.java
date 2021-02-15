package basic.ch06_object_association.ex_01_object_design_by_table_dependent.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "ch_06_ex_01_team_tb")
@Getter @Setter @NoArgsConstructor
@ToString
public class EX01_Team {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false, length = 30)
    private String teamName;
}

