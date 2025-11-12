/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Panen {
    private String idPanen;
    private String idPeriode;
    private Date tglPanen;
    private double jumlahPanen;
    
    // Constructor
    public Panen() {}
    
    public Panen(String idPanen, String idPeriode, Date tglPanen, double jumlahPanen) {
        this.idPanen = idPanen;
        this.idPeriode = idPeriode;
        this.tglPanen = tglPanen;
        this.jumlahPanen = jumlahPanen;
    }

    // Getter dan Setter
    public String getIdPanen() {
        return idPanen;
    }

    public void setIdPanen(String idPanen) {
        this.idPanen = idPanen;
    }

    public String getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(String idPeriode) {
        this.idPeriode = idPeriode;
    }

    public Date getTglPanen() {
        return tglPanen;
    }

    public void setTglPanen(Date tglPanen) {
        this.tglPanen = tglPanen;
    }

    public double getJumlahPanen() {
        return jumlahPanen;
    }

    public void setJumlahPanen(double jumlahPanen) {
        this.jumlahPanen = jumlahPanen;
    }
}

