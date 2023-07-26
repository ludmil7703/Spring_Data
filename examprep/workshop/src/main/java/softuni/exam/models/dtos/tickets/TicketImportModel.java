package softuni.exam.models.dtos.tickets;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportModel {
    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;
    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeoff;
    @XmlElement(name = "from-town")
    private TownBasicImportModel fromTown;
    @XmlElement(name = "to-town")
    private TownBasicImportModel toTown;
    @XmlElement(name = "passenger")
    private PassengerBasicImportModel passenger;
    public TicketImportModel() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    public TownBasicImportModel getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownBasicImportModel fromTown) {
        this.fromTown = fromTown;
    }

    public TownBasicImportModel getToTown() {
        return toTown;
    }

    public void setToTown(TownBasicImportModel toTown) {
        this.toTown = toTown;
    }

    public PassengerBasicImportModel getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerBasicImportModel passenger) {
        this.passenger = passenger;
    }
}
