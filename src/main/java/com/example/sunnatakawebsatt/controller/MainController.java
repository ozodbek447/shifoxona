package com.example.sunnatakawebsatt.controller;

import com.example.sunnatakawebsatt.DTO.ApiResponse;
import com.example.sunnatakawebsatt.entity.Navbat;
import com.example.sunnatakawebsatt.entity.User;
import com.example.sunnatakawebsatt.service.MainServer;
import com.example.sunnatakawebsatt.service.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final Server server;
    private final MainServer mainServer;

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        if (user.getAge()<=10){
            return "index";
        }
        if (server.filter(user)) {
            mainServer.getUsers.add(user);
            List<String> getUsers = mainServer.doctorList;
            model.addAttribute("doctors", getUsers);
            model.addAttribute("user", user); //
            return "main";

        }
        return "login";
    }





    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/tastiqlash")
    public String tastiqlash(@RequestParam String password,
                             @RequestParam String passport,
                             Model model) {

        if (server.checkPassport(passport, password)) {
            User user = mainServer.findUserByPassport(passport);
            List<String> doctors = mainServer.doctorList;
            model.addAttribute("doctors", doctors);
            model.addAttribute("user", user); // 👈 MUHIM
            return "main";
        }
        return "login";
    }

    @PostMapping("/navbatOlish")
    public String navbatOlish(Navbat navbat, Model model) {

        ApiResponse apiResponse = server.checkNavbat(navbat);
        model.addAttribute("user",apiResponse.getData());
        LocalDate now = LocalDate.now();
        if (now.isAfter(navbat.getDate())){
            model.addAttribute("errorMessage","vaqtni togri kitittinggiz");
            return "error";
        }

        if (apiResponse.isSuccess()) {
            int id=1;
            navbat.setNavbatRaqami(server.navbatBerish(navbat));
            navbat.setId(id);
            User user =(User) apiResponse.getData();
            user.getNavbats().add(navbat);
            model.addAttribute("tartibraqami", navbat.getNavbatRaqami());
            model.addAttribute("doctor", navbat.getDoctor());
            model.addAttribute("date", navbat.getDate());
            id++;
            return "success";

        }
        model.addAttribute("errorMessage", apiResponse.getMessage());
        return "error";

    }

    @GetMapping("/main")
    public String main(User user,Model model) {

        model.addAttribute("user", user);
        model.addAttribute("doctors", mainServer.doctorList);
        return "main";
    }

    @GetMapping("/main2")
    public String main2(@RequestParam String passport,Model model) {
        if (server.getUser(passport)==null) {
            model.addAttribute("errorMessage", "User not found");
            model.addAttribute("user", mainServer.findUserByPassport(passport));
            return "error";
        }
        model.addAttribute("user", server.getUser(passport));
        model.addAttribute("doctors", mainServer.doctorList);
        return "main";

    }
    @GetMapping("/navbat")
    public String navbat(String passport,Model model) {

        if (server.getUser(passport) == null) {
            model.addAttribute("errorMessage", "User not found");
            model.addAttribute("user", server.getUser(passport));
            return "error";
        }
        User user = server.getUser(passport);
        server.navbatDelete(user);
        model.addAttribute("user", user);
        model.addAttribute("navbat" ,user.getNavbats());
        return "navbat";
    }

    @PostMapping("/navbat/delete")
    public String delete(@RequestParam String passport,
                         @RequestParam int navbatId,Model model) {
        if (server.getUser(passport)==null) {
            model.addAttribute("errorMessage", "User not found");
            model.addAttribute("user", server.getUser(passport));
            return "error";
        }
        User user = server.getUser(passport);
        user.getNavbats().remove(navbatId);
        model.addAttribute("navbat", user.getNavbats());
        model.addAttribute("user", user);
        model.addAttribute("doctors", mainServer.doctorList);
        return "navbat";
    }




}
