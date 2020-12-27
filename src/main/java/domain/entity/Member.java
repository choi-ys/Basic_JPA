package domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@ToString
public class Member {

    @Id
    private long id;
    private String name;

    public Member() {
    }

    @Builder
    public Member(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
