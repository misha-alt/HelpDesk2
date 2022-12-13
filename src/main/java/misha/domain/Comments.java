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


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +

                '}';
    }
}
