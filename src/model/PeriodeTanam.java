/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class PeriodeTanam {
    private String idPeriode;
    private String idTanaman;
    private String idPetani;
    private Date tglMulaiTanam;
    private double jumlahTanam;
    private String status;
    private String lat;
    private String longi;
    
    // Constructor
    public PeriodeTanam() {}
    
    public PeriodeTanam(String idPeriode, String idTanaman, String idPetani, Date tglMulaiTanam, 
                        double jumlahTanam, String status, String lat, String longi) {
        this.idPeriode = idPeriode;
        this.idTanaman = idTanaman;
        this.idPetani = idPetani;
        this.tglMulaiTanam = tglMulaiTanam;
        this.jumlahTanam = jumlahTanam;
        this.status = status;
        this.lat = lat;
        this.longi = longi;
    }

    // Getter dan Setter
    public String getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(String idPeriode) {
        this.idPeriode = idPeriode;
    }

    public String getIdTanaman() {
        return idTanaman;
    }

    public void setIdTanaman(String idTanaman) {
        this.idTanaman = idTanaman;
    }

    public String getIdPetani() {
        return idPetani;
    }

    public void setIdPetani(String idPetani) {
        this.idPetani = idPetani;
    }

    public Date getTglMulaiTanam() {
        return tglMulaiTanam;
    }

    public void setTglMulaiTanam(Date tglMulaiTanam) {
        this.tglMulaiTanam = tglMulaiTanam;
    }

    public double getJumlahTanam() {
        return jumlahTanam;
    }

    public void setJumlahTanam(double jumlahTanam) {
        this.jumlahTanam = jumlahTanam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}

