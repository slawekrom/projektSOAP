package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PERSON")
public class Person implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_PERSON", nullable = false)
    private long id_person;

    @Column
    private String firstName;
    @Column
    private String secondName;


    public Person(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
