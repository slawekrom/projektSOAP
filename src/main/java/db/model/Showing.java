package db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    @JoinColumn(name = "ID_MOVIE")
    private Movie movie;
    @OneToMany(mappedBy = "showing", cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reservation> reservations = new ArrayList<Reservation>();

}
