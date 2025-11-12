/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Petani {
    private String idPetani;
    private String namaPetani;
    private String jenisKelamin;
    private String alamat;
    private String noHp;
    private double jumlahLahan;
    
    // Constructor
    public Petani() {}
    
    public Petani(String idPetani, String namaPetani, String jenisKelamin, String alamat, String noHp, double jumlahLahan) {
        this.idPetani = idPetani;
        this.namaPetani = namaPetani;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.noHp = noHp;
        this.jumlahLahan = jumlahLahan;
    }
    
    // Getter dan Setter
    public String getIdPetani() {
        return idPetani;
    }

    public void setIdPetani(String idPetani) {
        this.idPetani = idPetani;
    }

    public String getNamaPetani() {
        return namaPetani;
    }

    public void setNamaPetani(String namaPetani) {
        this.namaPetani = namaPetani;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public double getJumlahLahan() {
        return jumlahLahan;
    }

    public void setJumlahLahan(double jumlahLahan) {
        this.jumlahLahan = jumlahLahan;
    }
}

