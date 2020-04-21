package db.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name= "MOVIE")
public class Movie {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_MOVIE", nullable = false)
    private int id_movie;
    @Column
    String title;
    @Column
    private String description;
    @Column
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "ID_PERSON")
    private Person director;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MOVIE_PERSON",
            joinColumns = {@JoinColumn(name = "ID_MOVIE")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PERSON")})
    @Cascade(org.hibernate.annotations.CascadeType.ALL )
    private List<Person> actors = new ArrayList<Person>();

    @OneToMany(mappedBy = "MOVIE", cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Showing> showings = new ArrayList<Showing>();


}
