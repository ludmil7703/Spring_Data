package softuni.exam.models.dtos.planes;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneImportModel {
    @XmlElement(name = "register-number")
    @Size(min = 5)
    private String registerNumber;
    @XmlElement
    @Positive
    private int capacity;
    @XmlElement
    @Size(min = 2)
    private String airline;

    public PlaneImportModel() {
    }

    public PlaneImportModel(String registerNumber, int capacity, String airline) {
        this.registerNumber = registerNumber;
        this.capacity = capacity;
        this.airline = airline;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public PlaneImportModel setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public PlaneImportModel setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getAirline() {
        return airline;
    }

    public PlaneImportModel setAirline(String airline) {
        this.airline = airline;
        return this;
    }
}
