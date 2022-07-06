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

public class Utama {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader input = new BufferedReader(isr);
    
    Koneksi kon = new Koneksi();
    Connection conn = kon.conn;
    Statement stm = kon.stm;
    ResultSet rs;
    String sql;
    
    Menu mn = new Menu();
    
    public void register() throws IOException, AWTException, InterruptedException {
        System.out.println("Pendaftaran Desktop Banking BROLink");
        System.out.println("ketik 'batal' lalu enter untuk kembali");
        System.out.println("=========================================");
        System.out.println("Masukkan Nama Lengkap Anda");
        String namaLengkap = input.readLine().trim();
            if (namaLengkap.equals("batal")) { mn.menu(); }
        System.out.println("=========================================");
        System.out.println("Masukkan No. KTP Anda");
        String noKtp = input.readLine().trim();
            if (noKtp.equals("batal")) {
                mn.menu();
            } else if (noKtp.length() <= 15) {
                System.out.println("Masukkan No. KTP yang valid");
                System.out.println("=========================================");
                System.out.println("Masukkan No. KTP Anda");
                noKtp = input.readLine().trim();
                if (noKtp.equals("batal")) {
                    mn.menu();
                } else if (noKtp.length() <= 15) {
                    System.out.println("=========================================");
                    System.out.println("Silakan coba lagi nanti");
                    Thread.sleep(3000);
                    mn.menu();
                }
            }
        System.out.println("=========================================");
        System.out.println("Masukkan jenis kelamin L = Laki-laki/P = Perempuan");
        System.out.println("---------------> Ketik (L/P) saja <---------------");
        String jenKel = input.readLine().trim();
            if (jenKel.equals("batal")) {
                mn.menu();
            } else if (jenKel.length() > 2) {
                System.out.println("=========================================");
                System.out.println("Hanya L atau P saja");
                jenKel = input.readLine().trim();
                if (jenKel.equals("batal")) {
                    mn.menu();
                } else if (jenKel.length() > 2) {
                    System.out.println("=========================================");
                    System.out.println("Silakan coba lagi nanti");
                    Thread.sleep(3000);
                    mn.menu();
                }
            }
        System.out.println("=========================================");
        System.out.println("Masukkan Tanggal Lahir Anda format yyyy-mm-dd");
        System.out.println("-----------> Contoh: 1990-09-09 <------------");
        String tglLahir = input.readLine().trim();
            if (tglLahir.equals("batal")) { mn.menu(); }
        System.out.println("=========================================");
        System.out.println("Masukkan Alamat tempat tinggal Anda");
        String alamat = input.readLine().trim();
            if (alamat.equals("batal")) { mn.menu(); }
        System.out.println("=========================================");
        System.out.println("Masukkan Nomor Telepon Anda");
        String noTelp = input.readLine().trim();
            if (noTelp.equals("batal")) { mn.menu(); }
        System.out.println("=========================================");
        System.out.println("Masukkan Nama Ayah Anda");
        String namaAyah = input.readLine().trim();
            if (namaAyah.equals("batal")) { mn.menu(); }
        System.out.println("=========================================");
        System.out.println("Masukkan Nama Ibu Anda");
        String namaIbu = input.readLine().trim();
            if (namaIbu.equals("batal")) { mn.menu(); }
            
        
    }
}
