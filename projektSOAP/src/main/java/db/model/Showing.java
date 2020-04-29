package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "SHOWING")
public class Showing implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_SHOWING", nullable = false)
    private long id_showing;

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

    public Showing(Date date, Time time, String freePlaces, String occupiedPlaces, Movie movie) {
        this.date = date;
        this.time = time;
        this.freePlaces = freePlaces;
        this.occupiedPlaces = occupiedPlaces;
        this.movie = movie;
    }
}
