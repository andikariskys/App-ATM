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
import java.util.ArrayList;

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
                mn.menu();
                break;
            default:
                this.menuLogin();
                break;
        }
    }
    
    private void informasiSaldo() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Informasi Saldo");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Lihat Saldo");
        System.out.println("2. Lihat Transaksi Terakhir");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda --> ");
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
    
    private void lihatSaldo() throws IOException, AWTException {
        mn.clear();
        System.out.println("Lihat Saldo");
        System.out.println("=====================================");
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
        System.out.println("Ketik angka 0 lalu tekan enter untuk kembali");
        System.out.println("");
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
    
    private void transaksiTerakhir() throws IOException, AWTException {
        mn.clear();
        System.out.println("Lihat Transaksi Terakhir");
        System.out.println("=====================================");
        
        sql = "SELECT * FROM transaksi WHERE id_user= " + id + " AND status = 'sukses' "
                + "ORDER BY tgl_transaksi DESC LIMIT 5";
        kon.koneksi();
        try {
            int no = 1;
            String format = "%-3s | %-20s | %-20s | %-17s | %-20s | %-15s";
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
        System.out.println("Ketik angka 0 lalu tekan enter untuk kembali");
        System.out.println("");
        System.out.print("Pilihan Anda --> ");
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
      
    private void numberPenerima() throws IOException, AWTException {
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
    
    private void nominalTransfer() throws IOException, AWTException {
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
    
    private void validasiTransfer() throws IOException, AWTException {
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
                validasiPin("transfer");
                break;
            case "Y" : 
                validasiPin("transfer");
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
    
    private void validasiPin(String transaksi) throws IOException, AWTException {
        mn.clear();
        
        System.out.println("=====================================");
        System.out.println("Masukkan Pin Anda");
        String pinUser = input.readLine().trim();
        if(pinUser.equals(pin_login)) {
            switch (transaksi) {
                case "transfer":
                    simpanTransfer();
                    break;
                case "belipulsa" :
                    
                    break;
            }
        } else {
            this.validasiPin(transaksi);
        }
    }
    
    private void simpanTransfer() {
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
    
    private void transferSaldo() throws IOException, AWTException {
        numberPenerima();
        nominalTransfer();
        validasiTransfer();
    }
    
    private void pinLama() throws AWTException, IOException {
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
    
    private void pinBaru() throws IOException, AWTException {
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
    
    private void ubahPin() throws AWTException, IOException {
        pinLama();
        pinBaru();
    }
    
    private void hapusAkun() {
        sql = "DELETE FROM user WHERE id = " + id;
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void PembayaranOlshop() throws IOException, AWTException {
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
    
    private void PembayaranEMoney() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Pembayaran Olshop");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Shopeepay");
        System.out.println("2. Gopay");
        System.out.println("3. Lazada Credit");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda --> ");
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
    
    private void PembayaranLainnya() throws IOException, AWTException {
        mn.clear();
        
        System.out.println("Pembayaran Olshop");
        System.out.println("=====================================");
        System.out.println("Pilih menu lalu ketik angka (cth: 1),\nselanjutnya tekan enter");
        System.out.println("1. Tagihan PLN");
        System.out.println("2. Tagihan PDAM");
        System.out.println("3. Tagihan Indihome");
        System.out.println("4. Pulsa All Operator");
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
            case "4" :
                pembelianPulsa();
                this.PembayaranLainnya();
                break;
            case "0" :
                this.menuLogin();
                break;
            default:
                this.PembayaranLainnya();
        }
    }
    
    private void pembayaran(String nama) throws IOException, AWTException {
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
    
    private void validasiPembayaran(int nominalPembayaran, String kode, String nm_penerima) throws IOException, AWTException {
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
    
    private void simpanPembayaran(int saldoUser, int nominalPembayaran, String kode) throws IOException, AWTException {
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
    
    public void modeAdmin() throws IOException, AWTException {
        mn.clear();
        System.out.println("Mode Admin");
        System.out.println("=========================================");
        System.out.println("1. Tambah Saldo");
        System.out.println("2. Buat Pesanan");
        System.out.println("3. Hapus Pesanan");
        System.out.println("0. Keluar");
        System.out.print("Pilihan Anda --> ");
        String pilihan = input.readLine().trim();
        switch(pilihan) {
            case "1" :
                tambahSaldo();
                this.modeAdmin();
                break;
            case "2" :
                buatPesanan();
                this.modeAdmin();
                break;
            case "3" :
                hapusPesanan();
                this.modeAdmin();
                break;
            case "0" :
                mn.menu();
                break;
            default :
                this.modeAdmin();
                break;
        }
    }
    
    private void tambahSaldo() throws IOException, AWTException {
        mn.clear();
        String saldoUser = null;
        System.out.println("=====================================");
        sql = "SELECT saldo FROM user WHERE id=" + id;
        kon.koneksi();
        try {
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {                
                System.out.println("Saldo Anda : Rp. " + rs.getString(1));
                saldoUser = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=====================================");
        System.out.println("Masukkan jumlah penambahan saldo");
        System.out.println("(Tanpa meggunakan titik (.) )");
        System.out.println("Atau ketik 'batal' lalu enter untuk kembali");
        String tambahSaldo = input.readLine().trim();
        
        if (!tambahSaldo.equals("batal")) {
            int total = Integer.valueOf(tambahSaldo) + Integer.valueOf(saldoUser);
            sql = "UPDATE user SET saldo = " + total + " WHERE number_login= " + number_login;
            kon.koneksi();
            try {
                pst = kon.conn.prepareStatement(sql);
                pst.execute();
                System.out.println("Total Saldo Anda saat ini : " + total);
                mn.Delayy();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void buatPesanan() throws AWTException, IOException {
        mn.clear();
        System.out.println("=====================================");
        System.out.println("1. Pembayaran Shopee \n2. Pembayaran Tokopedia \n3. Pembayaran Lazada");
        System.out.println("4. Top Up Shopeepay \n5. Top Up Lazada Credit \n6. Top Up Gopay");
        System.out.println("7. Tagihan PLN \n8. Tagihan PDAM \n9. Tagihan Indihome \n0. Kembali");
        System.out.print("Pilihan Anda --> ");
        String pilihan = input.readLine().trim();
        
        switch (pilihan) {
            case "1" :
                pesanan("SHPE");
                this.buatPesanan();
                break;
            case "2" :
                pesanan("TKPD");
                this.buatPesanan();
                break;
            case "3" :
                pesanan("LZDA");
                this.buatPesanan();
                break;
            case "4" :
                pesanan("SHP");
                this.buatPesanan();
                break;
            case "5" :
                pesanan("LZC");
                this.buatPesanan();
                break;
            case "6" :
                pesanan("GOP");
                this.buatPesanan();
                break;
            case "7" :
                pesanan("LSTR");
                this.buatPesanan();
                break;
            case "8" :
                pesanan("AIR");
                this.buatPesanan();
                break;
            case "9" :
                pesanan("WFI");
                this.buatPesanan();
                break;
            case "0" :
                modeAdmin();
                break;
            default:
                buatPesanan();
                break;
        }
    }

    private void pesanan(String pilihan) throws AWTException, IOException {
        mn.clear();
        System.out.println("====================================="); //tambahkan batal
        System.out.println("Username Tujuan \nMasukkan yang benar karena tidak ada validasi");
        System.out.println("Ketik 'batal' lalu enter untuk kembali");
        String usernameTujuan = input.readLine().trim();
        if (usernameTujuan.equals("batal")) {
            buatPesanan();
        }
        
        System.out.println("=====================================");
        System.out.println("Nominal \nMasukkan yang benar karena tidak ada validasi");
        System.out.println("Ketik 'batal' lalu enter untuk kembali");
        String Nominal = input.readLine().trim();
        if (Nominal.equals("batal")) {
            buatPesanan();
        }
        
        simpanDataPesanan(pilihan, usernameTujuan, Nominal);
    }
    
    

    private void simpanDataPesanan(String pilihan, String usernameTujuan, String Nominal) {
        
        kon.koneksi();
        String digit8 = "9999995";
        String digit9 = "99999999";
        String kode = null;
        String tujuan = null;
        
        if (pilihan.equals("SHP") | pilihan.equals("LZC") | pilihan.equals("GOP") | pilihan.equals("AIR")) {
            sql = "SELECT FLOOR(RAND() * " + digit9 + ")";
            try {
                rs = kon.stm.executeQuery(sql);
                while (rs.next()) {                
                    String RN = rs.getString(1); // RN = Random Number
                    String NA = RN.replaceAll("\\.", ""); // NA = Nomor Acak
                    kode = NA.replaceAll("E", "");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            tujuan = pilihan + kode;
            
        } else {
            sql = "SELECT FLOOR(RAND() * " + digit8 + ")";
            try {
                rs = kon.stm.executeQuery(sql);
                while (rs.next()) {                
                    String RN = rs.getString(1); // RN = Random Number
                    String NA = RN.replaceAll("\\.", ""); // NA = Nomor Acak
                    kode = NA.replaceAll("E", "");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            tujuan = pilihan + kode;
        }
        
        String nm_transaksi = null;
        switch (pilihan) {
            case "SHPE":
                nm_transaksi = "Pembayaran Shopee";
                break;
            case "TKPD" :
                nm_transaksi = "Pembayaran Tokopedia";
                break;
            case "LZDA" :
                nm_transaksi = "Pembayaran Tokoedia";
                break;
            case "SHP" :
                nm_transaksi = "Top Up Shopeepay";
                break;
            case "LZC" :
                nm_transaksi = "Top Up Lazada Credit";
                break;
            case "GOP" :
                nm_transaksi = "Top Up Gopay";
                break;
            case "LSTR" :
                nm_transaksi = "Tagihan PLN";
                break;
            case "AIR" :
                nm_transaksi = "Tagihan PDAM";
                break;
            case "WFI" :
                nm_transaksi = "Tagihan Indihome";
                break;
            default:
                break;
        }
        
        sql = "INSERT INTO transaksi VALUES(null, null, null, '" + nm_transaksi + "', '" + 
                tujuan + "', '" + usernameTujuan + "', " + Nominal + ", 'Pending')";
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
            System.out.println("=====================================");
            System.out.println("Nama Transaksi : " + nm_transaksi);
            System.out.println("Kode Anda : " + tujuan);
            try {
                System.out.println("=====================================");
                System.out.println("SIlakan Copy/Screenshot Kode diatas \nTimer: 10 detik");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void hapusPesanan() throws AWTException, IOException {
        mn.clear();
        kon.koneksi();
        
        ArrayList dataid = new ArrayList();
        
        System.out.println("=====================================");
        try {
            sql = "SELECT * FROM transaksi WHERE status='Pending'";
            String format = "| %-3s | %-15s | %-20s | %-20s | %10s |";
            System.out.println(String.format(format, "ID", "Nama Transaksi", "Tujuan", 
                    "Nama Penerima", "Nominal"));
            rs = kon.stm.executeQuery(sql);
            
            while (rs.next()) {
                System.out.println(String.format(format, rs.getString(1), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7)));
                dataid.add(rs.getString(1));
            }
            System.out.println("\nPilih Id lalu tekan enter \nKetik 'batal' lalu tekan enter untuk kembali");
            System.out.print("Pilihan Anda --> ");
            String pilihan = input.readLine().trim();
            
            if (pilihan.equals("batal")) {
                modeAdmin();
            } else {
                for (int i = 0; i < dataid.size(); i++) {
                    if (pilihan.equals(dataid.get(i))) {
                        hpsPesanan(pilihan);
                    } else {
                        System.out.println("Masuk-kan Id yang benar");
                        mn.Delayy();
                        this.hapusPesanan();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void hpsPesanan(String pilihan) {
        sql = "DELETE FROM transaksi WHERE id=" + pilihan;
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
            System.out.println("Pesanan berhasil dihapus");
            mn.Delayy();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void pembelianPulsa() throws AWTException, IOException {
        mn.clear();
        
        System.out.println("=====================================");
        System.out.println("Masukkan Nomor Tujuan");
        String noTujuan = input.readLine().trim();
        if (noTujuan.equals("batal")) {
            PembayaranLainnya();
        } else if (noTujuan.length() < 11) {
            System.out.println("No Tidak valid");
            mn.Delayy();
            this.pembelianPulsa();
        } else {
            pilihNominalPulsa(noTujuan);
        }
    }
    
    private void pilihNominalPulsa(String noTujuan) throws IOException, AWTException {
        System.out.println("Pilih Nominal");
            System.out.println("1. 25.000 \n2. 50.000 \n3. 100.000 \n4. 200.000");
            System.out.println("\nKetik 'batal' lalu tekan enter untuk batal");
            System.out.println("Ketik 'ulangi' lalu tekan enter untuk mengganti nomor");
            System.out.println("Ketik angka antara 1-4 lalu tekan enter untuk melanjutkan");
            System.out.print("Pilihan Anda --> ");
            String pilihan = input.readLine().trim();
            
            switch (pilihan) {
                case "batal":
                    PembayaranLainnya();
                    break;
                case "ulangi":
                    this.pembelianPulsa();
                    break;
                default:
                    switch (pilihan) {
                        case "1" :
                            validasiPulsa(25000, noTujuan);
                            break;
                        case "2" :
                            validasiPulsa(50000, noTujuan);
                            break;
                        case "3" :
                            validasiPulsa(100000, noTujuan);
                            break;
                        case "4" :
                            validasiPulsa(200000, noTujuan);
                            break;
                    }   
                    break;
            }
    }
    
    private void validasiPulsa(int nominalPulsa, String noTujuan) throws IOException, AWTException {
        mn.clear();
        String provider = null;
        
        switch (noTujuan.substring(0, 3)) {
            case "081":
            case "082":
                provider = "Telkomsel";
                break;
            case "083":
                provider = "AXIS";
                break;
            case "085":
                provider = "Indosat";
                break;
            case "087":
                provider = "XL";
                break;
            case "088":
                provider = "Smartfren";
                break;
            case "089":
                provider = "Three";
                break;
            default:
                System.out.println("Nomor Tidak valid");
                mn.Delayy();
                pembelianPulsa();
                break;
        }
        
        System.out.println("=====================================");
        System.out.println("1.  Provider Tujuan : " + provider);
        System.out.println("    Nomor Tujuan    : " + noTujuan);
        System.out.println("2.  Nominal Pulsa   : " + nominalPulsa);
        System.out.println("=====================================");
        System.out.println("Ketik 'Y' jika ingin melanjutkan atau ketik '1' atau '2' lalu enter untuk mengubah");
        String pilihan = input.readLine().trim();
        
        switch (pilihan) {
            case "Y":
            case "y":
                beliPulsa(nominalPulsa, noTujuan, provider);
                break;
            case "1" :
                pembelianPulsa();
                break;
            case "2" :
                pilihNominalPulsa(noTujuan);
                break;
        }
    }

    private void beliPulsa(int nominalPulsa, String noTujuan, String provider) throws IOException, AWTException {
        validasiPin("belipulsa");
        
        sql = "SELECT saldo FROM user WHERE number_login=" + number_login;
        try {
            kon.koneksi();
            rs = kon.stm.executeQuery(sql);
            while (rs.next()) {
                saldo = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        int sisaSaldo = saldo - nominalPulsa;
        
        sql = "UPDATE user SET saldo = " + sisaSaldo + " WHERE number_login= " + number_login;
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        sql = "INSERT INTO transaksi VALUES(null, current_timestamp(), " + id + ", 'Pembelian Pulsa', '"
                + noTujuan + "', '" + provider + "', " + nominalPulsa + ", 'sukses')";
        try {
            pst = kon.conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("=====================================");
        System.out.println("Sisa Saldo Anda : " + sisaSaldo);
        System.out.println("=====================================");
        System.out.println("Transaksi Pulsa berhasil");
        mn.Delayy();
    }
}