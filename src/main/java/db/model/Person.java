package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_PERSON", nullable = false)
    private long id_person;

    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String pesel;
    @OneToMany(mappedBy = "person", cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<Movie>();

    public Person(String firstName, String secondName, String pesel) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
    }
}
