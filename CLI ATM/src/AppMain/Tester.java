/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AppMain;

/**
 * 
 * @author Andika Risky Septiawan
 */

//              Hanya untuk test coding saja

import java.sql.*;

public class Tester {
    public void sql() {
        Koneksi kon = new Koneksi();
        kon.koneksi();
        String data = "";
        try {
            String sql = "SELECT * FROM user WHERE id=1";
            ResultSet rs = kon.stm.executeQuery(sql);
            while (rs.next()) {                
                data = rs.getString(1);
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if (data.equals("")) {
            System.out.println("Data tidak ditemukan");
        }
    }
    
    public static void main(String[] args) {
        Tester n = new Tester();
        n.sql();
    }
}
