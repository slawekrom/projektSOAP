package db.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

@Entity(name= "FILM")
public class Film {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_FILM", nullable = false)
    private int id_film;
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
    @JoinTable(name = "FILM_PERSON",
            joinColumns = {@JoinColumn(name = "ID_FILM")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PERSON")})
    @Cascade(org.hibernate.annotations.CascadeType.ALL )
    private List<Person> actors = new ArrayList<Person>();

    @OneToMany(mappedBy = "film", cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Showing> showings = new ArrayList<Showing>();


}
