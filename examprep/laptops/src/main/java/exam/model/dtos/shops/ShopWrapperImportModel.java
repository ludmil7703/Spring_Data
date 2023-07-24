package exam.model.dtos.shops;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopWrapperImportModel {
    @XmlElement(name = "shop")
    private List<ShopImportModel> shops;
    public ShopWrapperImportModel() {
    }

    public List<ShopImportModel> getShops() {
        return shops;
    }

    public void setShops(List<ShopImportModel> shops) {
        this.shops = shops;
    }
}
