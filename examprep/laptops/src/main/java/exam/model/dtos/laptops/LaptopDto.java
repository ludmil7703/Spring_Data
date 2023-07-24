package exam.model.dtos.laptops;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import exam.model.entities.WarrantyType;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class LaptopDto {
    @Size(min = 8)
    private String macAddress;
    @Positive
    private Double cpuSpeed;
    @Range(min = 8,max = 128)
    private Integer ram;
    @Range(min = 128,max = 1024)
    private Integer storage;
    @Positive
    private BigDecimal price;
    @Size(min = 10)
    private String description;
    private String warrantyType;
    private ShopBaseImportModel shop;
    public LaptopDto() {
    }
    public boolean isValidWarrantyType(){
        try {
            WarrantyType.valueOf(this.warrantyType);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopBaseImportModel getShop() {
        return shop;
    }

    public void setShop(ShopBaseImportModel shop) {
        this.shop = shop;
    }
}
