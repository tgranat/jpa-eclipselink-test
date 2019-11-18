package jpatest.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String id;
    private String firstName;
    private String lastName;
    // annotate the Person.family field with @ManyToOne, making Person the owning side.
    // (JPA specification under section 2.9, it's a good practice to mark many-to-one side as the owning side)
    @ManyToOne
    private Family family;

    @Transient
    private String willNotBeStored = "";

    @OneToMany
    private List<Job> jobList = new ArrayList<Job>();

    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getWillNotBeStored() {
        return willNotBeStored;
    }

    public void setWillNotBeStored(String willNotBeStored) {
        this.willNotBeStored = willNotBeStored;
    }

    public List<Job> getJobList() {
        return this.jobList;
    }

    public void setJobList(List<Job> nickName) {
        this.jobList = nickName;
    }


}