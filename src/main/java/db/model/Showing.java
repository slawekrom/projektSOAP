package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "SHOWING")
public class Showing implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_SHOWING", nullable = false)
    private long id_showing;

    @Column
    private Date date;
    @Column
    private String freePlaces;
    @Column
    private String occupiedPlaces;
    @ManyToOne
    @JoinColumn(name = "ID_MOVIE")
    private Movie movie;

    public Showing(Date date, String freePlaces, String occupiedPlaces, Movie movie) {
        this.date = date;
        this.freePlaces = freePlaces;
        this.occupiedPlaces = occupiedPlaces;
        this.movie = movie;
    }
}
