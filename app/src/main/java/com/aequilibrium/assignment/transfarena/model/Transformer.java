package com.aequilibrium.assignment.transfarena.model;

import com.google.gson.annotations.SerializedName;

public class Transformer {

    private String id;
    private String name;
    private Integer strength;
    private Integer intelligence;
    private Integer speed;
    private Integer endurance;
    private Integer rank;
    private Integer courage;
    private Integer firepower;
    private Integer skill;
    private String team;
    @SerializedName("tea_icon")
    private String teamIcon;

    public Transformer(String name, Integer strength, Integer intelligence, Integer speed, Integer endurance, Integer rank, Integer courage, Integer firepower, Integer skill, String team) {
        this.name = name;
        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getCourage() {
        return courage;
    }

    public Integer getFirepower() {
        return firepower;
    }

    public Integer getSkill() {
        return skill;
    }

    public String getTeam() {
        return team;
    }

    public String getTeamIcon() {
        return teamIcon;
    }
}
