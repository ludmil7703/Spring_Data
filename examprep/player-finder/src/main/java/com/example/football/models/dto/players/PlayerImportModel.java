package com.example.football.models.dto.players;

import com.example.football.models.entity.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerImportModel {
    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;
    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;
    @XmlElement
    @Email
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement
    private Position position;
    @XmlElement(name = "town")
    private TownImportModel town;
    @XmlElement(name = "team")
    private TeamImportModel team;
    @XmlElement(name = "stat")
    private StatImportModel stat;
    public PlayerImportModel() {
    }

    public PlayerImportModel(String firstName, String lastName, String email, String birthDate, Position position, TownImportModel town, TeamImportModel team, StatImportModel stat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.position = position;
        this.town = town;
        this.team = team;
        this.stat = stat;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public TownImportModel getTown() {
        return town;
    }

    public void setTown(TownImportModel town) {
        this.town = town;
    }

    public TeamImportModel getTeam() {
        return team;
    }

    public void setTeam(TeamImportModel team) {
        this.team = team;
    }

    public StatImportModel getStat() {
        return stat;
    }

    public void setStat(StatImportModel stat) {
        this.stat = stat;
    }
}
