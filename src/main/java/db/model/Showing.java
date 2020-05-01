package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import utils.ProgramUtil;

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
    @Column(columnDefinition="TEXT")
    private String freePlaces;
    @Column(columnDefinition="TEXT")
    private String occupiedPlaces;
    @ManyToOne
    @JoinColumn(name = "ID_MOVIE")
    private Movie movie;

    public Showing(Date date, Movie movie) {
        this.date = date;
        this.freePlaces = ProgramUtil.initFreePlaces();
        this.occupiedPlaces = "";
        this.movie = movie;
    }
}
