package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name= "MOVIE")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_MOVIE", nullable = false)
    private long id_movie;
    @Column
    String title;
    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "ID_PERSON")
    private Person director;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MOVIE_ACTOR",
            joinColumns = {@JoinColumn(name = "ID_MOVIE")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PERSON")})
    @Cascade(org.hibernate.annotations.CascadeType.ALL )
    private List<Person> actors = new ArrayList<Person>();


    public Movie(String title, String description, Person director) {
        this.title = title;
        this.description = description;
        this.director = director;
    }
}
