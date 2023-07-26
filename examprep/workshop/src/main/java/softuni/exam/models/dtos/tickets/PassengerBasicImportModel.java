package softuni.exam.models.dtos.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "passenger")
@XmlAccessorType(XmlAccessType.FIELD)
public class PassengerBasicImportModel {
    @XmlElement
    private String email;

    public PassengerBasicImportModel() {
    }

    public PassengerBasicImportModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public PassengerBasicImportModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
