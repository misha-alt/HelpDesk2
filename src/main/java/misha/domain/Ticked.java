package misha.domain;

import javax.persistence.*;

@Entity
@Table(name = "TICKED")
public class Ticked {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable =  false)
    private int id;
    @Column( nullable = false)
    private String name;
    @Column( nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;


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

    @Override
    public String toString() {
        return "Ticked{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description +

                '}';
    }
}
