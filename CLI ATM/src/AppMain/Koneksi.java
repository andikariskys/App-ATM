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
import java.sql.*;
import javax.swing.JOptionPane;

public class Koneksi {
    Connection conn;
    Statement stm;
    
    public void koneksi() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String User = "root";
            String Pass = "";
            String url = "jdbc:mysql://localhost/cli_atm";
            
            conn = DriverManager.getConnection(url, User, Pass);
            stm = conn.createStatement();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
