package misha.domain;

import javax.persistence.*;
@Entity
@Table(name = "FEEDBACK")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;

    @Column(name = "rate", nullable =  false)
    private int rate;

    @Column(name = "date_rate", nullable =  false)
    private String date_rate;

    @Column(name = "text", nullable =  true)
    private String text;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.LAZY)
    @JoinColumn(name = "ticked_id")
    private Ticked ticked;

    public FeedBack() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate_rate() {
        return date_rate;
    }

    public void setDate_rate(String date_rate) {
        this.date_rate = date_rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticked getTicked() {
        return ticked;
    }

    public void setTicked(Ticked ticked) {
        this.ticked = ticked;
    }
}
