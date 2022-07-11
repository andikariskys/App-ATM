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
import java.awt.AWTException;
import java.io.*;
import java.sql.*;

public class MenuLogin {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader input = new BufferedReader(isr);
    
    Koneksi kon = new Koneksi();
    ResultSet rs;
    PreparedStatement pst;
    String sql;
    
    String id, nama_lengkap, number_login, pin_login;
    
    public void menuLogin() throws IOException, AWTException {
        Menu mn = new Menu();
        mn.clear();
        
        System.out.println("Selamat Datang di Desktop Banking BROLink");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Informasi Saldo");
        System.out.println("2. Pembelian/Pembayaran");
        System.out.println("3. Top Up e-Money");
        System.out.println("4. Top Up lainnya/Bayar Tagihan");
        System.out.println("5. Transfer");
        System.out.println("6. Ubah Pin");
        System.out.println("7. Hapus Akun");
        System.out.println("0. Keluar/Log Out \n");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "1" :
                informasiSaldo();
                break;
            case "2" :
                break;
            case "3" :
                break;
            case "4" :
                break;
            case "5" :
                break;
            case "6" :
                break;
            case "7" :
                break;
            case "0" :
                mn.menu();
                break;
            default:
                this.menuLogin();
                break;
        }
    }
    
    public void informasiSaldo() throws IOException {
        System.out.println("Informasi Saldo");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Lihat Saldo");
        System.out.println("2. Lihat Transaksi Terakhir");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "1" :
                lihatSaldo();
                break;
            case "2" :
                transaksiTerakhir();
                break;
            case "0" :
                break;
            default: 
                this.informasiSaldo();
                break;
        }
    }
    
    public void lihatSaldo() throws IOException {
        System.out.println("Lihat Saldo");
        System.out.println("=====================================");
        System.out.println("Ketik angka 0 lalu tekan enter untuk kembali");
        System.out.println("");
        
        sql = "SELECT saldo FROM user WHERE id=" + id;
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {                
                System.out.println("Saldo Anda : Rp. " + rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("");
        System.out.println("=====================================");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "0" :
                informasiSaldo();
                break;
            default:
                this.lihatSaldo();
                break;
        }
    }
    
    public void transaksiTerakhir() throws IOException {
        System.out.println("Lihat Transaksi Terakhir");
        System.out.println("=====================================");
        System.out.println("Ketik angka 0 lalu tekan enter untuk kembali");
        System.out.println("");
        
        sql = "SELECT * FROM transaksi WHERE id= " + id + " AND status = 'sukses' "
                + "ORDER BY tgl_transaksi DESC LIMIT 5";
        try {
            int no = 1;
            String format = "%-3s | %-12s | %-10s | %-15s | %-20s | %-15s";
            System.out.println(String.format(format, "No", "Tgl Transaksi", 
                    "Nama Transaksi", "Kode/No Tujuan", "Nama Penerima", "Nominal"));
            rs = kon.stm.executeQuery(sql);
            
            while (rs.next()) {
                System.out.println(String.format(format, no++, rs.getString(2), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("");
        System.out.println("=====================================");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "0" :
                informasiSaldo();
                break;
            default:
                this.transaksiTerakhir();
                break;
        }
    }
}
