package com.example.football.models.dto.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatWrapperImportModel {
    @XmlElement(name = "stat")
    private List<StatImportModel> stats;
    public StatWrapperImportModel() {
    }

    public List<StatImportModel> getStats() {
        return stats;
    }

    public void setStats(List<StatImportModel> stats) {
        this.stats = stats;
    }
}
