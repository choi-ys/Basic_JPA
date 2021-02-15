package basic.ch05_entity_mapping.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "Sequence_example_entity")
@SequenceGenerator(
        name = "SEQUENCE_EXAMPLE_ENTITY_SEQ_GENERATOR"
        , sequenceName = "SEQUENCE_EXAMPLE_ENTITY_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Getter @Setter @NoArgsConstructor
public class GeneratedValueSequenceExampleEntity {

    /** Query Example
     * create sequence SEQUENCE_EXAMPLE_ENTITY_SEQ start with 1 increment by 1
     *
     * create table Sequence_example_entity (
     *     id bigint not null,
     *     name varchar(255),
     *     primary key (id)
     * )
     *
     * call next value for SEQUENCE_EXAMPLE_ENTITY_SEQ
     */
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "SEQUENCE_EXAMPLE_ENTITY_SEQ_GENERATOR"
    )
    private long id;

    private String value;
}