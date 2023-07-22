package softuni.exam.models.dto.offers;




import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {

    @XmlElement
    private Double price;
    @XmlElement(name = "agent")
    private AgentBaseImport agent;
    @XmlElement(name = "apartment")
    private ApartmentBaseImport apartment;
    @XmlElement
    private String publishedOn;



    public ApartmentBaseImport getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentBaseImport apartment) {
        this.apartment = apartment;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AgentBaseImport getAgent() {
        return agent;
    }

    public void setAgent(AgentBaseImport agent) {
        this.agent = agent;
    }
}
