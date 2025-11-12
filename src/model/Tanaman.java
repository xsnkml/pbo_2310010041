/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Tanaman {
    private String idTanaman;
    private String namaTanaman;
    private String jenisTanaman;
    
    // Constructor
    public Tanaman() {}
    
    public Tanaman(String idTanaman, String namaTanaman, String jenisTanaman) {
        this.idTanaman = idTanaman;
        this.namaTanaman = namaTanaman;
        this.jenisTanaman = jenisTanaman;
    }
    
    // Getter dan Setter
    public String getIdTanaman() {
        return idTanaman;
    }
    
    public void setIdTanaman(String idTanaman) {
        this.idTanaman = idTanaman;
    }
    
    public String getNamaTanaman() {
        return namaTanaman;
    }
    
    public void setNamaTanaman(String namaTanaman) {
        this.namaTanaman = namaTanaman;
    }
    
    public String getJenisTanaman() {
        return jenisTanaman;
    }
    
    public void setJenisTanaman(String jenisTanaman) {
        this.jenisTanaman = jenisTanaman;
    }
}
