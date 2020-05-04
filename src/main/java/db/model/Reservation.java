package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "RESERVATION")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_RES", nullable = false)
    private long id_reservation;

    @Column
    private String places;
    @Column
    private Boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;
    @ManyToOne
    @JoinColumn(name = "ID_SHOWING")
    private Showing showing;

    public Reservation(String places, Boolean isPaid, User user, Showing showing) {
        this.places = places;
        this.isPaid = isPaid;
        this.user = user;
        this.showing = showing;
    }
}
