package domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
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
