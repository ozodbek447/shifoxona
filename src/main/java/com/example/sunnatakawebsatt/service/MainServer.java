package com.example.sunnatakawebsatt.service;

import com.example.sunnatakawebsatt.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class MainServer {

    public  List<User> getUsers =new ArrayList<>();
    public  List<String> doctorList=List.of("Jurayev Ozodbek ", "Dostonov Mamarayim ", "Eshmatov Gishmat");

    public User findUserByPassport(String passport) {
        for (User user : getUsers) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }
}
