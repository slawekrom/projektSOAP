package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity(name= "IMAGE")
public class Image implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_IMAGE", nullable = false)
    private long id_image;
    @Column(length = 16777215)
    private byte[] image;
    @OneToOne
    @JoinColumn(name = "ID_MOVIE")
    private Movie movie;

    public Image(byte[] image, Movie movie) {
        this.image = image;
        this.movie = movie;
    }
}
