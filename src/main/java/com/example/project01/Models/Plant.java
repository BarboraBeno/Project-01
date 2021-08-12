package com.example.project01.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Table
@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String plantName;
    private String latinName;
    private boolean forKids;
    private boolean used;
    private Integer strength;

    public Plant(String plantName, String latinName, Integer strength) {
        this.plantName = plantName;
        this.latinName = latinName;
        this.forKids = false;
        this.used = false;
        this.strength = strength;
    }

    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    private Problem problem;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
}
