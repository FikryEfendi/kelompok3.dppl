import java.util.*;

public class Belajar{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean kondisi = true;
        while (kondisi){
        System.out.println("Pilih bangun ruang");
        System.out.println("1. persegi");
        System.out.println("2. lingkaran");
        System.out.println("3. segitiga");
        System.out.println("4. keluar");

        System.out.print("masukan user: ");
        int masukan = input.nextInt();
        
        switch (masukan){
            case 1: 
            System.out.println("masukkan panjang sisi");
            int sisi = input.nextInt();
            int luasPersegi = sisi * sisi;
            System.out.println("Luas persegi = "+ luasPersegi + "\n");
            break;

            case 2:
            System.out.println("masukkan panjang jari jari");
            double jari = input.nextInt();
            double luasLingkaran = 3.14 * jari * jari;
            System.out.println("Luas persegi = "+ luasLingkaran + "\n");
            break;

            case 3:
            System.out.println("masukkan panjang alas");
            int alas = input.nextInt();
            System.out.println("masukkan panjang tinggi");
            int tinggi = input.nextInt();
            int luasSegitiga = alas * tinggi / 2;
            System.out.println("Luas persegi = "+ luasSegitiga + "\n");
            break;

            case 4:
            System.out.println("Terimakasih sudah menggunakan program");
            kondisi = false;
            break;
        }

        }
    }
}