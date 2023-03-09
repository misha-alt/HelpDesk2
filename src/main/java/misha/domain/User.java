package misha.domain;



import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable =  false)
    private int id;


    @Column( nullable = false)
    @NotNull(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String first_name;


    @Column( nullable = false)
    @NotNull(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String last_name;


    @Column(nullable = false)
    private String login;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String authority;

    @NotNull(message = "email should not be empty")
    @Email(message = "email should de valid")
    @Column(nullable = false)
    private String email;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Comments> comments;

    /*Lists for Ticket*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_TICKED",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "ticked_id"))
    private Set<Ticked> ticked;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*Getters and Setters Ticket lists*/




    public Set<Ticked> getTicked() {
        return ticked;
    }

    public void setTicked(Set<Ticked> ticked) {
        this.ticked = ticked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authority='" + authority + '\'' +
                ", comments=" + comments +
                ", ticked=" + ticked +
                ", email=" + email +
                '}';
    }
}


