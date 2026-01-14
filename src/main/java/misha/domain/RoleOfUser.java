package misha.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USERROLES")
public class RoleOfUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable =  false)
    private int id;

    @Column(name = "role_name")
    private String role_name;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "authority")
    private Set<User> userSet;

    public RoleOfUser() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
}
