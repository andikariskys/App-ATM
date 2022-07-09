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
    ResultSet rs;
    PreparedStatement pst;
    String sql;
    
    public void kembaliMenu() throws IOException, AWTException {
        Menu mn = new Menu();
        mn.menu();
    }
    
    String namaLengkap, noKtp, jenKel, tglLahir, alamat, namaAyah, namaIbu, numberLogin, pinLogin;
    
    public void NamaLengkap() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan Nama Lengkap Anda, lalu tekan Enter");
        namaLengkap = input.readLine().trim();
            if (namaLengkap.equals("batal")) { kembaliMenu(); }
    }
    
    public void NoKtp() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan No. KTP Anda");
        noKtp = input.readLine().trim();
            if (noKtp.equals("batal")) {
                kembaliMenu();
            } else if (noKtp.length() != 16 && noKtp.length() != 17) {
                ulangi("NoKtp");
            }
    }
    
    public void JenisKelamin() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan jenis kelamin L = Laki-laki/P = Perempuan");
        System.out.println("---------------> Ketik (L/P) saja <---------------");
        jenKel = input.readLine().trim();
            if (jenKel.equals("batal")) {
                kembaliMenu();
            } else if (jenKel.length() >= 2) {
                ulangi("JenisKelamin");
            } else if (!jenKel.equals("L") && !jenKel.equals("P")) {
                ulangi("JenisKelamin");
            }
    }
    
    public void TanggalLahir() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan Tahun Lahir Anda (1961-2006)");
        String Thn = input.readLine().trim();
        int intThn = Integer.parseInt(Thn);
            if (Thn.equals("batal")) {
                kembaliMenu(); 
            } else if (Thn.length() != 4) {
                System.out.println("Tahun tidak valid");
                ulangi("TanggalLahir");
            } else if (intThn <= 1960 || intThn  >= 2007) {
                System.out.println("Hanya 1961-2007 saja");
                ulangi("TanggalLahir");
            }
            
        System.out.println("Masukkan Bulan Lahir Anda (01-12)");
        String Bln = input.readLine().trim();
        int intBln = Integer.parseInt(Bln);
            if (Bln.equals("batal")) {
                kembaliMenu(); 
            } else if (Bln.length() != 2) {
                System.out.println("Gunakan 0 jika dibawah 10 atau bulan yang valid");
                ulangi("TanggalLahir");
            } else if (intBln <= 0 || intBln  >= 13) {
                System.out.println("Bulan tidak valid");
                ulangi("TanggalLahir");
            }
            
        System.out.println("Masukkan Tanggal Lahir Anda (01-31)");
        String Tgl = input.readLine().trim();
        int intTgl = Integer.parseInt(Tgl);
            if (Tgl.equals("batal")) {
                kembaliMenu(); 
            } else if (Tgl.length() != 2) {
                System.out.println("Gunakan 0 jika dibawah 10 atau tanggal yang valid");
                ulangi("TanggalLahir");
            } else if (intTgl <= 0 || intTgl  >= 32) {
                System.out.println("Tanggal tidak valid");
                ulangi("TanggalLahir");
            } else if ((Tgl + Bln).equals("3002") || (Tgl + Bln).equals("3102")) {
                System.out.println("Februari hanya sampai tanggal 29");
                ulangi("TanggalLahir");
            }
            
        tglLahir = Thn + "-" + Bln + "-" + Tgl;
    }
    
    public void Alamat() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan Alamat tempat tinggal Anda");
        alamat = input.readLine().trim();
            if (alamat.equals("batal")) { kembaliMenu(); }
    }
    
    public void NamaAyah() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan Nama Ayah Anda");
        namaAyah = input.readLine().trim();
            if (namaAyah.equals("batal")) { kembaliMenu(); }
    }
    
    public void NamaIbu() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan Nama Ibu Anda");
        namaIbu = input.readLine().trim();
            if (namaIbu.equals("batal")) { kembaliMenu(); }
    }
    
    public void validasiData() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Apakah data dibawah ini sudah benar?");
        System.out.println("1. Nama Lengkap  : " + namaLengkap);
        System.out.println("2. No. KTP       : " + noKtp);
        System.out.println("3. Jenis Kelamin : " + jenKel);
        System.out.println("4. Tanggal Lahir : " + tglLahir);
        System.out.println("5. Alamat        : " + alamat);
        System.out.println("6. Nama Ayah     : " + namaAyah);
        System.out.println("7. Nama Ibu      : " + namaIbu);
        System.out.println("=========================================");
        System.out.println("Ketik 'Ya' atau 'Y' lalu tekan enter jika sudah benar");
        System.out.println("Jika terjadi kesalahn ketik angka 1-7, \nlalu tekan enter");
        String pilihan = input.readLine().trim();
        switch(pilihan) {
            case "Ya" :
                System.out.println("=========================================");
                System.out.println("Terima Kasih");
                break;
            case "Y" :
                System.out.println("=========================================");
                System.out.println("Terima Kasih");
                break;
            case "1" :
                ulangi("NamaLengkap");
                ulangi("validasi");
                break;
            case "2" :
                ulangi("NoKtp");
                ulangi("validasi");
                break;
            case "3" :
                ulangi("JenisKelamin");
                ulangi("validasi");
                break;
            case "4" :
                ulangi("TanggalLahir");
                ulangi("validasi");
                break;
            case "5" :
                ulangi("Alamat");
                ulangi("validasi");
                break;
            case "6" :
                ulangi("NamaAyah");
                ulangi("validasi");
                break;
            case "7" :
                ulangi("NamaIbu");
                ulangi("validasi");
                break;
            default:
                ulangi("validasi");
        }
    }
    
    public void generateNumberLogin() {
        kon.koneksi();
        sql = "SELECT FLOOR(RAND() * 999999999999)";
        String nl = null;
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {                
                nl = rs.getString(1);
            }
            String NL = nl.replaceAll("\\.", "");
            numberLogin = NL.replaceAll("E", "52");
            System.out.println("=========================================");
            System.out.println("Nomor Login Anda : " + numberLogin);
        } catch (SQLException e) {
            System.out.println("Maaf terjadi kesalahan pada server");
        }
    }
    
    public void PinLogin() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Buat PIN Anda");
        pinLogin = input.readLine().trim();
            if (pinLogin.equals("batal")) {
                kembaliMenu();
            } else if (pinLogin.length() <= 3 || pinLogin.length() >= 5) {
                ulangi("PinLogin");
            }
    }
    
    public void ulangi(String ulang) throws IOException, AWTException {
        switch(ulang) {
            case "NamaLengkap" :
                NamaLengkap();
                break;
            case "NoKtp":
                NoKtp();
                break;
            case "JenisKelamin" :
                JenisKelamin();
                break;
            case "TanggalLahir" :
                TanggalLahir();
                break;
            case "Alamat" :
                Alamat();
                break;
            case "NamaAyah" :
                NamaAyah();
                break;
            case "NamaIbu" :
                NamaIbu();
                break;
            case "validasi" :
                validasiData();
                break;
            case "PinLogin" :
                PinLogin();
                break;
            case "numberLogin" :
                numberLogin();
                break;
        }
    }
    
    public void simpanData() {
        kon.koneksi();
        try {
                sql = "INSERT INTO user VALUES(null, '" + namaLengkap + "', " + noKtp + ", '" +
                        jenKel + "', '" + tglLahir + "', '" + alamat + "', '" + namaAyah + "', '" + 
                        namaIbu + "', " + numberLogin + ", " + pinLogin + ", 0);";
                pst = kon.conn.prepareStatement(sql);
                pst.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    
    public void registerData() throws IOException, AWTException {
        System.out.println("Pendaftaran Desktop Banking BROLink");
        System.out.println("ketik 'batal' lalu enter untuk kembali");
        NamaLengkap();
        NoKtp();
        JenisKelamin();
        TanggalLahir();
        Alamat();
        NamaAyah();
        NamaIbu();
        validasiData();
        generateNumberLogin();
        PinLogin();
        simpanData();
    }
    
    public void numberLogin() throws IOException, AWTException {
        System.out.println("=========================================");
        System.out.println("Masukkan Nomor Login Anda");
        numberLogin = input.readLine().trim();
            if (numberLogin.equals("batal")) { 
                kembaliMenu(); 
            } else if (numberLogin.length() != 16 && numberLogin.length() != 17) {
                ulangi("numberLogin");
            }
    }
    
    public void cariData() throws IOException, AWTException, InterruptedException {
        kon.koneksi();
        sql = "SELECT * FROM user WHERE number_login = " + numberLogin + 
                " and no_ktp= " + noKtp + " and nama_ayah= '" + namaAyah + 
                "' and nama_ibu= '" + namaIbu + "';";
        String data = "";
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {                
                data = rs.getString(2);
                ubahPin(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if (data.equals("")) {
            System.out.println("=========================================");
            System.out.println("Mohon maaf, Data tidak ditemukan");
            Thread.sleep(3000);
        }
    }
    
    public void ubahPin(String idUser, String namaUser) throws IOException, AWTException, InterruptedException {
        System.out.println("=========================================");
        System.out.println("Nama Anda : " + namaUser);
        System.out.println("=========================================");
        System.out.println("Masukkan Pin baru Anda");
        String pinBaru = input.readLine().trim();
            if (pinBaru.equals("batal")) {
                kembaliMenu();
            } else if (pinBaru.length() <= 3 || pinBaru.length() >= 5) {
                ulangiUbahPin(idUser, namaUser);
            }
            
        System.out.println("=========================================");
        System.out.println("Pin baru Anda : " + pinBaru);
        System.out.println("Apakah Anda sudah yakin? Tekan 'Y' untuk melanjutkan & \n"
                + "'N' untuk mengulangi, dan 'batal' untuk kembali ke menu utama");
        String validasi = input.readLine().trim();
            switch(validasi) {
                case "batal" :
                    kembaliMenu();
                    break;
                case "Y" :
                    System.out.println("Tunggu sebentar ...");
                    simpanPinBaru(pinBaru, idUser);
                    break;
                case "N" :
                    ulangiUbahPin(idUser, namaUser);
                    break;
            }
            
        
    }
    
    public void ulangiUbahPin(String idUser, String namaUser) throws IOException, AWTException, InterruptedException {
        ubahPin(idUser, namaUser);
    }
    
    public void simpanPinBaru(String pinBaru, String idUser) throws InterruptedException {
        kon.koneksi();
        sql = "UPDATE user SET pin_login= " + pinBaru + " WHERE id= " + idUser +";";
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
            System.out.println("Pin berhasil diubah");
            Thread.sleep(3000);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void lupaPin() throws IOException, AWTException, InterruptedException {
        System.out.println("Lupa PIN Desktop Banking BROLink");
        System.out.println("ketik 'batal' lalu enter untuk kembali");
        numberLogin();
        NoKtp();
        NamaAyah();
        NamaIbu();
        cariData();
    }
}
