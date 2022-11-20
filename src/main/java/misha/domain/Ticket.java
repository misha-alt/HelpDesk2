package misha.domain;

import javax.persistence.*;

@Entity
@Table(name = "TICKED")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private String name;
    @Column( nullable = false)
    private String description;
    @Column( nullable = false)
    private String assignee_id;
    @Column( nullable = false)
    private String owner_id;
    @Column( nullable = false)
    private String category_id;
    @Column( nullable = false)
    private String approver_id;


    public Ticket() {

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

    public String getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(String assignee_id) {
        this.assignee_id = assignee_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getApprover_id() {
        return approver_id;
    }

    public void setApprover_id(String approver_id) {
        this.approver_id = approver_id;
    }
}
