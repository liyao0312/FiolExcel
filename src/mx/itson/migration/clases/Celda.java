/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.migration.clases;

/**
 *
 * @author SaulUrias
 */
public class Celda {
    private String info;
    private int columna;

    public Celda(String info, int columna) {
        this.info = info;
        this.columna = columna;
    }
    

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
