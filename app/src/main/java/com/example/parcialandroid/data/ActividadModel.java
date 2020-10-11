package com.example.parcialandroid.data;

public class ActividadModel {
    public int ID;
    public String nombreActividad;
    public int corte;
    public int IdMateria;
    public int Percent;

    public ActividadModel(int ID, String nombreActividad, int corte, int idMateria, int percent) {
        this.ID = ID;
        this.nombreActividad = nombreActividad;
        this.corte = corte;
        IdMateria = idMateria;
        Percent =  percent;
    }

    public void setModel() {
        this.ID = 0;
        this.nombreActividad = "";
        this.corte = 0;
        this.IdMateria = 0;
        this.Percent = 0;
    }
}
