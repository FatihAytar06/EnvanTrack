package com.example.mystockss.Class;

import java.util.ArrayList;

public class User {
    String isim,birim,email,yetkiSeviyesi;
    static final private ArrayList<User> userList = new ArrayList<>();

    public User( ) {
    }

    public User(String isim, String birim, String email, String yetkiSeviyesi) {
        this.isim = isim;
        this.birim = birim;
        this.email = email;
        this.yetkiSeviyesi = yetkiSeviyesi;

        User user = new User();
        user.setBirim(birim);
        user.setIsim(isim);
        user.setEmail(email);
        user.setYetkiSeviyesi(yetkiSeviyesi);

        userList.add(user);

    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getBirim() {
        return birim;
    }

    public void setBirim(String birim) {
        this.birim = birim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYetkiSeviyesi() {
        return yetkiSeviyesi;
    }

    public void setYetkiSeviyesi(String yetkiSeviyesi) {
        this.yetkiSeviyesi = yetkiSeviyesi;
    }
    static public ArrayList<User> getData(){


        return userList;
    }


}
