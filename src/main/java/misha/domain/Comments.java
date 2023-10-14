package misha.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USERS_COMMENT")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true,nullable = false)
    private int id;

    @Column(name = "text")
    private String comment;

    @Column(name = "comment_date")
    private String date;

    @Column(name = "loginOfCreator")
    private String loginOfCreator;



    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ticked_id")
    private Ticked ticked;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Comments (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Ticked getTicked() {
        return ticked;
    }

    public void setTicked(Ticked ticked) {
        this.ticked = ticked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoginOfCreator() {
        return loginOfCreator;
    }

    public void setLoginOfCreator(String loginOfCreator) {
        this.loginOfCreator = loginOfCreator;
    }

    @Override
    public String toString() {
        return  '\'' + "comment-'" + comment + '\'' +
                ", date-'" + date + '\'';
    }
}
