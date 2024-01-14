package misha.domain;

import javax.persistence.*;
@Entity
@Table(name = "PASSWORDS")

public class PasswordClass {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Column(name = "password")
    private String password;


    @OneToOne
    @JoinColumn(name = "user_id")

    private User user;


    public PasswordClass() {

    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

