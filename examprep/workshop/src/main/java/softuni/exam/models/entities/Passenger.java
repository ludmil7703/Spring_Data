package softuni.exam.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity{
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private int age;
    @Column
    private String phoneNumber;
    @ManyToOne
    private Town town;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "passenger",cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Passenger() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
