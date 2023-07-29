package softuni.exam.models.dto.astronomers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomersWrapperImportModel {
    @XmlElement(name = "astronomer")
    List<AstronomerImportModel> astronomers;
    public AstronomersWrapperImportModel() {
    }

    public List<AstronomerImportModel> getAstronomers() {
        return astronomers;
    }

    public void setAstronomers(List<AstronomerImportModel> astronomers) {
        this.astronomers = astronomers;
    }
}
