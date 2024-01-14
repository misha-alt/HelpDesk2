package misha.domain;




import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Set;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable =  false)
    private int id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String login;

    /*@Column(nullable = false)
    private String password;*/

   /* @Column(nullable = false)
    private String authority;*/

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Comments> comments;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<FeedBack> feedBacks;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "USER_TICKED",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "ticked_id"))
    private Set<Ticked> ticked;


    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "USERROLE_ID"))
    private Set<RoleOfUser> authority;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PasswordClass password;

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


   /* public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/



  /*  public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    /*Getters and Setters Ticket lists*/




    public Set<Ticked> getTicked() {
        return ticked;
    }

    public void setTicked(Set<Ticked> ticked) {
        this.ticked = ticked;
    }

    public Set<FeedBack> getFeedBacks() {
        return feedBacks;
    }

    public void setFeedBacks(Set<FeedBack> feedBacks) {
        this.feedBacks = feedBacks;
    }

    public Set<RoleOfUser> getAuthority() {
        return authority;
    }

    public void setAuthority(Set<RoleOfUser> authority) {
        this.authority = authority;
    }

    public PasswordClass getPassword() {
        return password;
    }

    public void setPassword(PasswordClass password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                /*", authority='" + authority + '\'' +*/
                ", comments='" + comments + '\'' +
                ", ticked=" + ticked +
                ", email=" + email +
                '}';
    }
}


