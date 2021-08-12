package com.example.project01.Models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String problem;

    public Problem(String problem) {
        this.problem = problem;
    }

    @OneToMany(mappedBy = "problem")
    private List<Plant> listOfPlants;
}
