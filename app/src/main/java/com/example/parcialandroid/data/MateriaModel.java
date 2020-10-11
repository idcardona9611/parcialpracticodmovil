package com.example.parcialandroid.data;

public class MateriaModel {
    public int ID;
    public String nombre;

    public MateriaModel(int ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public void setModel() {
        this.ID = 0;
        this.nombre = "";
    }
}
