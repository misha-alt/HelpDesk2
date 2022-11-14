package misha.domain;

import javax.persistence.*;
/*@Entity
@Table(name = "FEEDBACK")*/
public class FeedBack {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;

    @Column(name = "rate", nullable =  false)
    private int rate;

    @Column(name = "date_rate", nullable =  false)
    private String date_rate;

    @Column(name = "text", nullable =  false)
    private String text;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Users users;

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
    }*/
}
