package softuni.exam.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane extends BaseEntity{
    @Column(unique = true)
    private String registerNumber;
    @Column
    private int capacity;
    @Column
    private String airline;

    public Plane() {
    }

    public Plane(String registerNumber, int capacity, String airline) {
        this.registerNumber = registerNumber;
        this.capacity = capacity;
        this.airline = airline;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
