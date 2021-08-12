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

@Table
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userId;

    private String loginName;
    private String password;

    public User(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }

    @OneToMany(mappedBy = "user")
    private List<Plant> myList;
}
