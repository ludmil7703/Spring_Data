package softuni.exam.models.dtos.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketWrapperImportModel {
    @XmlElement(name = "ticket")
    List<TicketImportModel> tickets;
    public TicketWrapperImportModel() {
    }

    public List<TicketImportModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketImportModel> tickets) {
        this.tickets = tickets;
    }
}
