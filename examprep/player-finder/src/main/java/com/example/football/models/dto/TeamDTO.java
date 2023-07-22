package com.example.football.models.dto;

import com.example.football.models.entity.Town;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.util.List;

public class TeamDTO {
    @Size(min = 3)
    private String name;
    @Size(min = 3)
    private String stadiumName;
    @Range(min = 1000)
    private Integer fanBase;
    @Size(min = 10)
    private String history;

    private String townName;

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public TeamDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Integer getFanBase() {
        return fanBase;
    }

    public void setFanBase(Integer fanBase) {
        this.fanBase = fanBase;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }


}
