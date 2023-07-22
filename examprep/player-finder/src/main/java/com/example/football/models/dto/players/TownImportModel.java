package com.example.football.models.dto.players;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownImportModel {
    @XmlElement
    private String name;
    public TownImportModel() {
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
