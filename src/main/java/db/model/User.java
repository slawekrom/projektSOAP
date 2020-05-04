package db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "USER")
public class User {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "ID_USER", nullable = false)
    private long id_user;

    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String pesel;
    @Column()
    private String password;

    public User(String firstName, String secondName, String pesel, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
        this.password = password;
    }
}
