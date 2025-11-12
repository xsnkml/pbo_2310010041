/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import java.sql.*;

public class TestKoneksi {
    public static void main(String[] args) {
        System.out.println("=== TEST KONEKSI DATABASE ===\n");
        
        String url = "jdbc:mysql://localhost:3306/pbo_2310010041";
        String user = "root";
        String pass = "";
        
        try {
            System.out.println("1. Loading driver MySQL...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("   ✓ Driver berhasil dimuat!\n");
            
            System.out.println("2. Mencoba koneksi ke database...");
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("   ✓ Koneksi berhasil!\n");
            
            System.out.println("3. Info koneksi:");
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("   Database: " + meta.getDatabaseProductName());
            System.out.println("   Versi: " + meta.getDatabaseProductVersion());
            System.out.println("   URL: " + url + "\n");
            
            System.out.println("4. Mencoba query tabel petani...");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM petani");
            
            if (rs.next()) {
                System.out.println("   ✓ Query berhasil!");
                System.out.println("   Total data: " + rs.getInt("total") + " baris\n");
            }
            
            System.out.println("5. Menampilkan data petani:");
            rs = stmt.executeQuery("SELECT * FROM petani");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("   " + count + ". " + rs.getString("id_petani") + " | " + rs.getString("nama_petani"));
            }
            
            if (count == 0) {
                System.out.println("   (Tabel kosong)");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("\n✅ SEMUA TEST BERHASIL!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: Driver MySQL tidak ditemukan!");
            System.err.println("   Pastikan mysql-connector-java sudah ditambahkan ke library project");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ ERROR SQL: " + e.getMessage());
            System.err.println("\nKemungkinan penyebab:");
            System.err.println("1. MySQL tidak running");
            System.err.println("2. Database tidak ada");
            System.err.println("3. Username/password salah");
            e.printStackTrace();
        }
    }
}
