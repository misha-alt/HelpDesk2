package misha.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TICKED")
public class Ticked   {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;
    @Column( nullable = false)
    private String assignee;
    @Column(nullable = false)
    private String approver;
    @Column(nullable = false)
    private String rollOfCreater;
    @Column (nullable = false)
    private  String loginOfcreater;
    @Column( nullable = false)
    private String name;
    @Column( nullable = false)
    private String description;


    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    public State state;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @Column( nullable = false)
    private String desireddate;

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
                '}';
    }


}
