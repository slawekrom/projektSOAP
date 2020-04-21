package db.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Entity(name = "SHOWING")
public class Showing {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_SHOWING", nullable = false)
    private int id_showing;

    @Column
    private Date date;
    @Column
    private Time time;
    @Column
    private String freePlaces;
    @Column
    private String occupiedPlaces;
    @ManyToOne
    @JoinColumn(name = "ID_FILM")
    private Film film;
    @OneToMany(mappedBy = "showing", cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reservation> reservations = new ArrayList<Reservation>();

}
