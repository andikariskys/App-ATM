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
    
    Menu mn = new Menu();
    
    String id, nama_lengkap, number_login, pin_login;
    
    public void menuLogin() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Selamat Datang di Desktop Banking BROLink");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Informasi Saldo");
        System.out.println("2. Pembayaran Olshop");
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
                this.menuLogin();
                break;
            case "2" :
                PembayaranOlshop();
                this.menuLogin();
                break;
            case "3" :
                PembayaranEMoney();
                break;
            case "4" :
                PembayaranLainnya();
                break;
            case "5" :
                transferSaldo();
                this.menuLogin();
                break;
            case "6" :
                ubahPin();
                break;
            case "7" :
                hapusAkun();
                break;
            case "0" :
                break;
            default:
                this.menuLogin();
                break;
        }
    }
    
    public void informasiSaldo() throws IOException, AWTException {
        mn.clear();
        
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
                menuLogin();
                break;
            default: 
                this.informasiSaldo();
                break;
        }
    }
    
    public void lihatSaldo() throws IOException, AWTException {
        mn.clear();
        System.out.println("Lihat Saldo");
        System.out.println("=====================================");
        System.out.println("Ketik angka 0 lalu tekan enter untuk kembali");
        System.out.println("");
        
        sql = "SELECT saldo FROM user WHERE id=" + id;
        kon.koneksi();
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
    
    public void transaksiTerakhir() throws IOException, AWTException {
        mn.clear();
        System.out.println("Lihat Transaksi Terakhir");
        System.out.println("=====================================");
        System.out.println("Ketik angka 0 lalu tekan enter untuk kembali");
        System.out.println("");
        
        sql = "SELECT * FROM transaksi WHERE id= " + id + " AND status = 'sukses' "
                + "ORDER BY tgl_transaksi DESC LIMIT 5";
        kon.koneksi();
        try {
            int no = 1;
            String format = "%-3s | %-20s | %-15s | %-17s | %-20s | %-15s";
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
    
    String number_penerima, nama_penerima = "";
    int nominal, saldo, saldoPenerima;
      
    public void numberPenerima() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Transfer Saldo");
        System.out.println("=====================================");
        System.out.println("Masukkan Nomor Tujuan");
        number_penerima = input.readLine().trim();
            if (number_penerima.equals("batal")) { 
                this.menuLogin();
            } else if (number_penerima.length() != 16 && number_penerima.length() != 17) {
                this.numberPenerima();
            }
            
        sql = "SELECT nama_lengkap FROM user WHERE number_login = " + number_penerima + 
                " AND NOT number_login = " + number_login;
        kon.koneksi();
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {                
                nama_penerima = rs.getString(1);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        
        if (nama_penerima.equals("")) {
            System.out.println("Data tidak ditemukan");
            mn.Delayy();
            this.numberPenerima();
        }
    }
    
    public void nominalTransfer() throws IOException, AWTException {
        System.out.println("=====================================");
        System.out.println("Masukkan Nominal");
        nominal = Integer.parseInt(input.readLine().trim());
        
        sql = "SELECT saldo FROM user WHERE number_login= " + number_login;
        kon.koneksi();
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {
                saldo = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if (saldo < nominal) {
            System.out.println("Nominal tidak mencukupi");
            System.out.println("Saldo Anda : " + saldo);
            mn.Delayy();
            this.nominalTransfer();
        }
    }
    
    public void validasiTransfer() throws IOException, AWTException {
        System.out.println("=====================================");
        System.out.println("    Saldo Anda          : " + saldo);
        System.out.println("1.  No. Tujuan          : " + number_penerima);
        System.out.println("2.  Nominal Transfer    : " + nominal );
        System.out.println("    Nama Tujuan         : " + nama_penerima);
        System.out.println("");
        System.out.println("Ketik 'Ya' atau 'Y' lalu tekan enter jika sudah benar");
        System.out.println("atau ketik 'batal' lalu tekan enter untuk membatalkan");
        System.out.println("Jika terjadi kesalahan ketik angka 1 atau 2, \nlalu tekan enter");
        System.out.print("Pilihan Anda --> ");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "Ya" : 
                validasiPin();
                break;
            case "Y" : 
                validasiPin();
                break;
            case "1" :
                numberPenerima();
                this.validasiTransfer();
                break;
            case "2" :
                nominalTransfer();
                this.validasiTransfer();
                break;
            case "batal" :
                menuLogin();
                break;
            default:
                this.validasiTransfer();
                break;
        }
    }
    
    public void validasiPin() throws IOException {
        System.out.println("=====================================");
        System.out.println("Masukkan Pin Anda");
        String pinUser = input.readLine().trim();
        if(pinUser.equals(pin_login)) {
            simpanTransfer();
        } else {
            this.validasiPin();
        }
    }
    
    public void simpanTransfer() {
        sql = "SELECT saldo FROM user WHERE number_login = " + number_penerima;
        kon.koneksi();
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {
                saldoPenerima = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        String saldoAkhirTujuan = String.valueOf(saldoPenerima + nominal);
        String saldoAkhirUser = String.valueOf(saldo - nominal);
        
        sql = "UPDATE user SET saldo = " + saldoAkhirTujuan + " WHERE number_login= " + number_penerima;
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        sql = "UPDATE user SET saldo = " + saldoAkhirUser + " WHERE number_login= " + number_login;
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        sql = "INSERT INTO transaksi VALUES(null, current_timestamp(), " + id + ", 'Transfer Saldo', '"
                + number_penerima + "', '" + nama_penerima + "', " + nominal + ", 'sukses')";
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Transfer Saldo berhasil dilakukan...");
        System.out.println("Sisa Saldo Anda : " + saldoAkhirUser);
        mn.Delayy();
    }
    
    public void transferSaldo() throws IOException, AWTException {
        numberPenerima();
        nominalTransfer();
        validasiTransfer();
    }
    
    public void pinLama() throws AWTException, IOException {
        mn.clear();
        
        System.out.println("=====================================");
        System.out.println("Masukkan Pin lama Anda (4 Digit) atau \n"
                + "ketik 'batal' untuk membatalkan lalu tekan enter");
        String pinLama = input.readLine().trim();
        if (!pinLama.equals(pin_login)) {
            this.pinLama();
        } else if (pinLama.equals("batal")) {
            menuLogin();
        } else if (pinLama.length() <= 3 || pinLama.length() >= 5) {
            this.pinLama();
        }
    }
    
    public void pinBaru() throws IOException, AWTException {
        System.out.println("=====================================");
        System.out.println("Masukkan Pin baru Anda (4 Digit) atau \n"
                + "ketik 'batal' untuk membatalkan lalu tekan enter");
        String pinBaru = input.readLine().trim();
        if (pinBaru.equals("batal")) {
            menuLogin();
        } else if (pinBaru.length() <= 3 || pinBaru.length() >= 5) {
            this.pinBaru();
        } else if (pinBaru.equals(pin_login)) {
            this.pinBaru();
        }
        
        sql = "UPDATE user SET pin_login = " + pinBaru + " WHERE number_login = " + number_login;
        kon.koneksi();
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
            System.out.println("=====================================");
            System.out.println("Pin berhasil diubah");
            mn.Delayy();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void ubahPin() throws AWTException, IOException {
        pinLama();
        pinBaru();
    }
    
    public void hapusAkun() {
        sql = "DELETE FROM user WHERE id = " + id;
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void PembayaranOlshop() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Pembayaran Olshop");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Shopee");
        System.out.println("2. Tokopedia");
        System.out.println("3. Lazada");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "1" :
                pembayaran("Shopee");
                break;
            case "2" :
                pembayaran("Tokopedia");
                break;
            case "3" :
                pembayaran("Lazada");
                break;
            case "0" :
                this.menuLogin();
                break;
            default:
                this.PembayaranOlshop();
        }
    }
    
    public void PembayaranEMoney() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Pembayaran Olshop");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Shopeepay");
        System.out.println("2. Gopay");
        System.out.println("3. Lazada Credit");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "1" :
                pembayaran("Shopeepay");
                break;
            case "2" :
                pembayaran("Gopay");
                break;
            case "3" :
                pembayaran("Lazada Credit");
                break;
            case "0" :
                this.menuLogin();
                break;
            default:
                this.PembayaranEMoney();
        }
    }
    
    public void PembayaranLainnya() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Pembayaran Olshop");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Tagihan PLN");
        System.out.println("2. Tagihan PDAM");
        System.out.println("3. Tagihan Indihome");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda -->");
        String pilihan = input.readLine().trim();
        switch (pilihan) {
            case "1" :
                pembayaran("Tagihan PLN");
                break;
            case "2" :
                pembayaran("Tagihan PDAM");
                break;
            case "3" :
                pembayaran("Tagihan Indihome");
                break;
            case "0" :
                this.menuLogin();
                break;
            default:
                this.PembayaranLainnya();
        }
    }
    
    public void pembayaran(String nama) throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Pembayaran " + nama);
        System.out.println("=====================================");
        System.out.println("Masukkan Kode Pembayaran");
        System.out.println("atau ketik 'batal' lalu enter untuk kembali");
        String kode = input.readLine().trim();
        
        switch (nama) {
            case "Shopee" :
                if (kode.equals("batal")) {
                    PembayaranOlshop();
                } else if (!kode.substring(0, 4).equals("SHPE") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                }
                break;
            case "Tokopedia" :
                if (kode.equals("batal")) {
                    PembayaranOlshop();
                } else if (!kode.substring(0, 4).equals("TKPD") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                }
                break;
            case "Lazada" :
                if (kode.equals("batal")) {
                    PembayaranOlshop();
                } else if (!kode.substring(0, 4).equals("LZDA") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                }
                break;
            case "Shopeepay" :
                if (kode.equals("batal")) {
                    this.PembayaranEMoney();
                } else if (!kode.substring(0, 3).equals("SHP") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                } 
                break;
            case "Gopay" :
                if (kode.equals("batal")) {
                    this.PembayaranEMoney();
                } else if (!kode.substring(0, 3).equals("GPY") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                } 
                break;
            case "Lazada Credit" :
                if (kode.equals("batal")) {
                    this.PembayaranEMoney();
                } else if (!kode.substring(0, 3).equals("LZC") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                } 
                break;
            case "Tagihan PLN" :
                if (kode.equals("batal")) {
                    this.PembayaranLainnya();
                } else if (!kode.substring(0, 3).equals("LST")) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                } else if (kode.length() != 12) {
                    this.pembayaran(nama);
                }
                break;
            case "Tagihan PDAM" :
                if (kode.equals("batal")) {
                    this.PembayaranLainnya();
                } else if (!kode.substring(0, 3).equals("AIR") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                }
                break;
            case "Tagihan Indihome" :
                if (kode.equals("batal")) {
                    this.PembayaranLainnya();
                } else if (!kode.substring(0, 3).equals("WFI") || kode.length() != 12) {
                    System.out.println("=====================================");
                    System.out.println("Kode yang Anda masukkan tidak valid");
                    mn.Delayy();
                    
                    this.pembayaran(nama);
                } 
                break;
        }
        
        String data = "", status = "", nm_penerima = "";
        int nominalPembayaran = 0;
        
        sql = "SELECT * FROM transaksi WHERE tujuan = '" + kode + "'";
        kon.koneksi();
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {
                data = "ada";
                status = rs.getString("status");
                nominalPembayaran = rs.getInt("nominal");
                nm_penerima = rs.getString("nama_penerima");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if (data.equals("")) {
            System.out.println("=====================================");
            System.out.println("Maaf, kode tidak ditemukan");
            mn.Delayy();
            
            this.pembayaran(nama);
        } else if (status.equals("sukses")) {
            System.out.println("=====================================");
            System.out.println("Transaksi telah diselesaikan");
            mn.Delayy();
        }
        
        validasiPembayaran(nominalPembayaran, kode, nm_penerima);
    }
    
    public void validasiPembayaran(int nominalPembayaran, String kode, String nm_penerima) throws IOException, AWTException {
        int saldoUser = 0;
        
        sql = "SELECT saldo FROM user WHERE id = " + id;
        kon.koneksi();
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {
                saldoUser = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        System.out.println("=====================================");
        System.out.println("Saldo Anda      : " + saldoUser);
        System.out.println("Username Tujuan : " + nm_penerima);
        System.out.println("Nominal         : " + nominalPembayaran);
        System.out.println("=====================================");
        System.out.println("Apakah Anda yakin ingin melanjutkan?");
        System.out.println("Ketik 'Y' lalu enter untuk melanjutkan");
        System.out.println("Atau ketik 'batal' lalu enter untuk kembali");
        String pilihan = input.readLine().trim();
        
        switch (pilihan) {
            case "batal" :
                this.menuLogin();
                break;
            case "Y" :
                if (saldoUser < nominalPembayaran) {
                    System.out.println("=====================================");
                    System.out.println("Saldo Anda tidak mencukupi");
                    mn.Delayy();
                    
                    this.menuLogin();
                } else {
                    simpanPembayaran(saldoUser, nominalPembayaran, kode);
                }
                break;
            default:
                this.validasiPembayaran(nominalPembayaran, kode, nm_penerima);
                break;
        }
    }
    
    public void simpanPembayaran(int saldoUser, int nominalPembayaran, String kode) throws IOException, AWTException {
        int total = saldoUser - nominalPembayaran;
        
        sql = "UPDATE user SET saldo = " + total + " WHERE number_login= " + number_login;
        kon.koneksi();
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        sql = "UPDATE transaksi SET tgl_transaksi = current_timestamp(), id_user = " + id + ", "
                + "status='sukses' WHERE tujuan = '" + kode + "'";
        kon.koneksi();
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("=====================================");
        System.out.println("Transaksi Berhasil");
        mn.Delayy();
        this.menuLogin();
    }
}
