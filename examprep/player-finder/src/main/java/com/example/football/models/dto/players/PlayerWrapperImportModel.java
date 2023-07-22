package com.example.football.models.dto.players;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerWrapperImportModel {
    @XmlElement(name = "player")
    List<PlayerImportModel> players;
    public PlayerWrapperImportModel() {
    }

    public List<PlayerImportModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerImportModel> players) {
        this.players = players;
    }
}
