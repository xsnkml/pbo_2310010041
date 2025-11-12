/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import config.Koneksi;
import java.sql.*;

public class IDGenerator {
    
    public static String generateID(String table, String column, String prefix) {
        String newID = prefix + "001";
        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "SELECT MAX(" + column + ") as maxID FROM " + table;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String lastID = rs.getString("maxID");
                if (lastID != null) {
                    int num = Integer.parseInt(lastID.substring(prefix.length()));
                    num++;
                    newID = prefix + String.format("%03d", num);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error generate ID: " + e.getMessage());
        }
        return newID;
    }
}
