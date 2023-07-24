package exam.model.dtos.shops;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportModel {
    @XmlElement
    @Size(min = 4)
    private String address;
    @XmlElement(name = "employee-count")
    @Range(min = 1,max = 50)
    private Integer employeeCount;
    @XmlElement
    @Range(min = 20000)
    private Integer income;
    @XmlElement
    @Size(min = 4)
    private String name;

    @XmlElement(name = "shop-area")
    @Range(min = 150)
     private Integer shopArea;
    @XmlElement(name = "town")
    private TownBaseModel town;
    public ShopImportModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public TownBaseModel getTown() {
        return town;
    }

    public void setTown(TownBaseModel town) {
        this.town = town;
    }
}
