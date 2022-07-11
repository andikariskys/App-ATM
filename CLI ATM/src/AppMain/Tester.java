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

public class Tester {
    public void sql() {
        String cetak = "";
        System.out.println(String.format("%-10s | %8s | %8s", "hello", "helli", "hella\n"));
        
        System.out.format("%-10s | %8s | %8s", "hello", "helli", "hella\n");
        System.out.format("%-10s | %8s | %8s", "dennyszta", "barkrode", "terizawa");
        System.out.println("");
        
        System.out.println(String.format("%-3s | %-12s | %-10s | %-15s | %-20s | %-15s", 
                "No", "Tgl Transaksi", "Nama Transaksi", "Kode/No Tujuan", "Nama Penerima", "Nominal"));
    }
    
    public static void main(String[] args) {
        Tester n = new Tester();
        n.sql();
    }
}
