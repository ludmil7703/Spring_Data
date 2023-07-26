package softuni.exam.models.dtos.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownBasicImportModel {
    @XmlElement
    private String name;

    public TownBasicImportModel() {
    }

    public TownBasicImportModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
