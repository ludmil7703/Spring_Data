package softuni.exam.models.dto.offers;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferWrapperDTO {
    @XmlElement(name = "offer")
    private List<OfferImportDTO> offers;

    public List<OfferImportDTO> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferImportDTO> offers) {
        this.offers = offers;
    }
}
