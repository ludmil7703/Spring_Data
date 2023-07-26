package softuni.exam.models.dtos.planes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneWrapperImportModel {
    @XmlElement(name = "plane")
    List<PlaneImportModel> planes;

    public PlaneWrapperImportModel() {
    }

    public List<PlaneImportModel> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlaneImportModel> planes) {
        this.planes = planes;
    }
}
