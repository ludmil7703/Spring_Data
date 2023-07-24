package exam.model.dtos.towns;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownWrapperImportModel {
    @XmlElement(name = "town")
    List<TownImportModel> towns;
    public TownWrapperImportModel() {
    }

    public List<TownImportModel> getTowns() {
        return towns;
    }

    public void setTowns(List<TownImportModel> towns) {
        this.towns = towns;
    }
}
