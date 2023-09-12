package com.example.mystockss.Class;

import java.util.ArrayList;

public class Rol {
    private String rol;

    static final private ArrayList<Rol> rollerrList = new ArrayList<>();

    public Rol() {
    }

    public Rol(String rol) {
        this.rol = rol;
        Rol roller = new Rol();
        roller.setRol(rol);
        rollerrList.add(roller);
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    static public ArrayList<Rol> getData(){

        return rollerrList;
    }
}
