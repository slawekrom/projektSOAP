package db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_RES", nullable = false)
    private int id_eservation;

    @Column
    private String places;
    @Column
    private Boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "ID_PERSON")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "ID_SHOWING")
    private Showing showing;

}
