package com.example.sunnatakawebsatt.service;

import com.example.sunnatakawebsatt.DTO.ApiResponse;
import com.example.sunnatakawebsatt.entity.Navbat;
import com.example.sunnatakawebsatt.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class Server {

    private final MainServer mainServer;

    public  boolean  filter(User user) {
        if(user.getName()!=null && user.getAge()==0&&user.getPassword()==null){
            return false;
        }
        if (user.getPassport()!=null && user.getPassport().length()!=9){
            return false;
        }
        char c =user.getPassport().charAt(0);

        if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
           return false;
        }
        return true;
    }

    public boolean  checkPassport(String passport,String password) {
        for (User user : mainServer.getUsers) {
            if (user.getPassport().equals(passport)) {
                if (user.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
    public User  getUser(String passport) {
        for (User user : mainServer.getUsers) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

    public ApiResponse checkNavbat(Navbat navbat) {

        if (navbat == null) {
            return new ApiResponse(false, "Navbat ma'lumoti yuborilmadi", null);
        }

        // PASSPORT TEKSHIRISH
        if (navbat.getPassport() == null || navbat.getPassport().isBlank()) {
            return new ApiResponse(false, "Passport raqami kiritilmadi", null);
        }

        User user = getUser(navbat.getPassport());
        if (user == null) {
            return new ApiResponse(false, "Foydalanuvchi mavjud emas", null);
        }

        // DOCTOR TEKSHIRISH
        if (navbat.getDoctor() == null || navbat.getDoctor().isBlank()) {
            return new ApiResponse(false, "Doctor tanlanmadi", null);
        }

        // HAMMASI TO‘G‘RI
        return new ApiResponse(true, "Navbat olish mumkin", user);
    }

    public int navbatBerish(Navbat navbat) {

        int raqami=0;
        for (User getUser : mainServer.getUsers) {
            for (Navbat getUserNavbat : getUser.getNavbats()) {
                if (getUserNavbat.getDoctor().equals(navbat.getDoctor())&&getUserNavbat.getDate().equals(navbat.getDate())) {
                    raqami = getUserNavbat.getNavbatRaqami();
                    if (raqami<getUserNavbat.getNavbatRaqami()) {
                        raqami=getUserNavbat.getNavbatRaqami();
                    }
                }
            }
        }
        return raqami+1;

    }

    public void navbatDelete(User user) {
        LocalDate now = LocalDate.now();

        user.getNavbats().removeIf(navbat ->
                now.isAfter(navbat.getDate())
        );
    }


}
