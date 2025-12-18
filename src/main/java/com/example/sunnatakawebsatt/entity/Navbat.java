package com.example.sunnatakawebsatt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Navbat {

    private int id;
    private String doctor;
    private LocalDate date;
    private String passport;
    private int navbatRaqami;


}
