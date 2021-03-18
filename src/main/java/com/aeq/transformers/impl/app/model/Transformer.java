package com.aeq.transformers.impl.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Entity to represent a Transformer.
 */
@Entity
public class Transformer implements Comparable<Transformer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Team is required")
    @Size(min = 1, max = 1, message = "Team should be one character")
    @Pattern(regexp = "^[AD]$", message = "Team should be either 'A' or 'D'")
    private String team;

    @NotNull(message = "Strength is required")
    @Min(value = 1, message = "Strength should be between 1 to 10")
    @Max(value = 10, message = "Strength should be between 1 to 10")
    private Integer strength;

    @NotNull(message = "Intelligence is required")
    @Min(value = 1, message = "Intelligence should be between 1 to 10")
    @Max(value = 10, message = "Intelligence should be between 1 to 10")
    private Integer intelligence;

    @NotNull(message = "Speed is required")
    @Min(value = 1, message = "Speed should be between 1 to 10")
    @Max(value = 10, message = "Speed should be between 1 to 10")
    private Integer speed;

    @NotNull(message = "Endurance is required")
    @Min(value = 1, message = "Endurance should be between 1 to 10")
    @Max(value = 10, message = "Endurance should be between 1 to 10")
    private  Integer endurance;

    @NotNull(message = "Rank is required")
    @Min(value = 1, message = "Rank should be between 1 to 10")
    @Max(value = 10, message = "Rank should be between 1 to 10")
    private Integer rank;

    @NotNull(message = "Courage is required")
    @Min(value = 1, message = "Courage should be between 1 to 10")
    @Max(value = 10, message = "Courage should be between 1 to 10")
    private Integer courage;

    @Column(name="FIREPOWER")
    @NotNull(message = "Firepower is required")
    @Min(value = 1, message = "Firepower should be between 1 to 10")
    @Max(value = 10, message = "Firepower should be between 1 to 10")
    private Integer firePower;

    @NotNull(message = "Skill is required")
    @Min(value = 1, message = "Skill should be between 1 to 10")
    @Max(value = 10, message = "Skill should be between 1 to 10")
    private Integer skill;

    public Transformer () {}

    public Transformer(Long id, String name, String team,
                       Integer strength, Integer intelligence, Integer speed,
                       Integer endurance, Integer rank, Integer courage,
                       Integer firePower, Integer skill) {
             this.id = id;
             this.name = name;
             this.team = team;
             this.strength = strength;
             this.intelligence = intelligence;
             this.speed = speed;
             this.endurance = endurance;
             this.rank = rank;
             this.courage = courage;
             this.firePower = firePower;
             this.skill = skill;
    }

    public Integer getOverallRating() {
        return (this.strength + this.intelligence + this.speed + this.endurance + this.firePower );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getCourage() {
        return courage;
    }

    public void setCourage(Integer courage) {
        this.courage = courage;
    }

    public Integer getFirePower() {
        return firePower;
    }

    public void setFirePower(Integer firePower) {
        this.firePower = firePower;
    }

    public Integer getSkill() {
        return skill;
    }

    public void setSkill(Integer skill) {
        this.skill = skill;
    }

    @JsonIgnore
    public int compareTo(Transformer other) {
        return other.rank - this.rank;
    }
}
