package byAJ.Securex.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")//, fetch = FetchType.LAZY)
    private Collection<Userz> users;


    // ==== Constructor ====
    public Role()
    {
        this.users=new ArrayList<Userz>();
    }


    // ==== Setters and Getters ====

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<Userz> getUsers() {
        return users;
    }

    public void setUsers(Collection<Userz> users) {
        this.users = users;
    }



    //==== Method to add User object to collections of users that have a particular Role ====
    public void addUser (Userz user){
        this.users.add(user);
    }


}
