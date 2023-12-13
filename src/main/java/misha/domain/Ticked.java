package misha.domain;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "TICKED")
public class Ticked   {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categor categor;//           category

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "approver")
    private String approver;

    @Column(nullable = false)
    private String rollOfCreater;
    @Column (nullable = false)
    private  String loginOfcreater;
    @Column( nullable = false)
    private String name;//              name
    @Column( nullable = false)
    private String description;//        description

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    public State state;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Urgency urgency;//             urgen

    @Column( nullable = false)
    private String desireddate;//       desire date

    @Column(nullable = false)    //create date
    private String create_date;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "ticked_id")
    private Set<Comments> comments;

    @OneToMany(/*cascade = CascadeType.ALL,*/fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<FeedBack> feedBacks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ticked_id")
    private Set <MyFile> myFile;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "ticked")
    private Set <User> user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ticked_id")
    private Set<Tickethistory> tickethistories;

    public Ticked() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getRollOfCreater() {
        return rollOfCreater;
    }

    public void setRollOfCreater(String rollOfCreater) {
        this.rollOfCreater = rollOfCreater;
    }

    public String getLoginOfcreater() {
        return loginOfcreater;
    }

    public void setLoginOfcreater(String loginOfcreater) {
        this.loginOfcreater = loginOfcreater;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public String getDesireddate() {
        return desireddate;
    }

    public void setDesireddate(String desireddate) {
        this.desireddate = desireddate;
    }

    public Categor getCategor() {
        return categor;
    }

    public void setCategor(Categor categor) {
        this.categor = categor;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public Set<MyFile> getMyFile() {
        return myFile;
    }

    public void setMyFile(Set<MyFile> myFile) {
        this.myFile = myFile;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Set<Tickethistory> getTickethistories() {
        return tickethistories;
    }

    public void setTickethistories(Set<Tickethistory> tickethistories) {
        this.tickethistories = tickethistories;
    }

    public Set<FeedBack> getFeedBacks() {
        return feedBacks;
    }

    public void setFeedBacks(Set<FeedBack> feedBacks) {
        this.feedBacks = feedBacks;
    }

    @Override
    public String toString() {
        return "Ticked{" +
                "id=" + id +
                ", assignee='" + assignee + '\'' +
                ", approver='" + approver + '\'' +
                ", rollOfCreater='" + rollOfCreater + '\'' +
                ", loginOfcreater='" + loginOfcreater + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state.getCat() +
                ", urgency=" + urgency +
                ", date='" + desireddate + '\'' +
                ", caegor='" + categor + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }


}
