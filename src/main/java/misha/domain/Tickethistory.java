package misha.domain;

import misha.Valdator.MyValidator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TICKETHISTORY")
public class Tickethistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;

    @Column(name = "dateHistory")
    private String dateHistory;

    @Column(name = "nameHistory")
    private String nameHistory;

    @Column(name = "ticket_description")
    private String ticket_description;


    @Column(name = "deletedFilename")
    private String deletedFilename;



    @ManyToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.LAZY)
    @JoinColumn(name = "ticked_id")
    private Ticked ticked;


   @OneToMany(fetch = FetchType.EAGER)
   @JoinColumn(name = "history_id")
    private Set<MyFile> myFiles;

    public Tickethistory() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateHistory() {
        return dateHistory;
    }

    public void setDateHistory(String dateHistory) {
        this.dateHistory = dateHistory;
    }


    public String getNameHistory() {
        return nameHistory;
    }

    public void setNameHistory(String nameHistory) {
        this.nameHistory = nameHistory;
    }

    public String getTicket_description() {
        return ticket_description;
    }

    public void setTicket_description(String ticket_description) {
        this.ticket_description = ticket_description;
    }


    public Ticked getTicked() {
        return ticked;
    }

    public void setTicked(Ticked ticked) {
        this.ticked = ticked;
    }

    public Set<MyFile> getMyFiles() {
        return myFiles;
    }

    public void setMyFiles(Set<MyFile> myFiles) {
        this.myFiles = myFiles;
    }

    public String getDeletedFilename() {
        return deletedFilename;
    }

    public void setDeletedFilename(String deletedFilename) {
        this.deletedFilename = deletedFilename;
    }

    @Override
    public String toString() {
        return "TicketHistory{" +
                "id=" + id +
                ", dateHistory='" + dateHistory + '\'' +
                ", ticket_description='" + ticket_description + '\'' +
                '}';
    }
}
