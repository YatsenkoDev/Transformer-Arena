package com.aequilibrium.assignment.transfarena.model;

import android.support.annotation.NonNull;

import com.aequilibrium.assignment.transfarena.utils.MathUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transformer implements Serializable, Comparable<Transformer> {

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
    @SerializedName("team_icon")
    private String teamIcon;

    public Transformer(String id, String name, PowerParameters powerParameters, String team) {
        this.id = id;
        this.name = name;
        this.strength = powerParameters.strength;
        this.intelligence = powerParameters.intelligence;
        this.speed = powerParameters.speed;
        this.endurance = powerParameters.endurance;
        this.rank = powerParameters.rank;
        this.courage = powerParameters.courage;
        this.firepower = powerParameters.firepower;
        this.skill = powerParameters.skill;
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

    @Override
    public int compareTo(@NonNull Transformer transformer) {
        return rank < transformer.rank ? 1 : -1;
    }

    public static class PowerParameters {
        private Integer strength;
        private Integer intelligence;
        private Integer speed;
        private Integer endurance;
        private Integer rank;
        private Integer courage;
        private Integer firepower;
        private Integer skill;

        public PowerParameters(Integer strength, Integer intelligence, Integer speed, Integer endurance, Integer rank, Integer courage, Integer firepower, Integer skill) {
            this.strength = strength;
            this.intelligence = intelligence;
            this.speed = speed;
            this.endurance = endurance;
            this.rank = rank;
            this.courage = courage;
            this.firepower = firepower;
            this.skill = skill;
        }
    }
}
