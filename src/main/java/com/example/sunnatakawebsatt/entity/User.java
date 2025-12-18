package com.example.sunnatakawebsatt.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private int id;
    private String name;
    private String passport;
    private String password;
    private int age;
    private List<Navbat> navbats= new ArrayList<>();


}
