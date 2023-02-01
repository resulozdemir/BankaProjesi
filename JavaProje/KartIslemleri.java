package BankaProjesi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

public class KartIslemleri{
    private static String kartNo;
    private static String cvv;
    private static String sonKullanmaTarihi;
    private static String internetAlisverisi;

    public KartIslemleri(){
        kartNo="4";         //visa kartlar 4 ile baslar
        cvv="";
        sonKullanmaTarihi="";
        internetAlisverisi="kapali";
    }

    public void kartOlustur(File dosya,int flag){
        Random rastgele = new Random();
        int temp;
        for(int i=0;i<15;i++){              //kart no olusturma
            temp = rastgele.nextInt(10);
            kartNo = kartNo.concat(Integer.toString(temp));
            if(i==2 || i == 6 || i == 10){
                kartNo = kartNo.concat(" ");
            }
        }
        System.out.println("Kart Numarasi : " + kartNo);

        for(int i=0;i<3;i++){              //cvv olusturma
            temp = rastgele.nextInt(10);
            cvv = cvv.concat(Integer.toString(temp));
        }
        System.out.println("Kart CVV : " + cvv);

        int yil = rastgele.nextInt(23, 30);   //son kullanma tarihi olusturma 2023 ile 2029 arasindas
        int ay = rastgele.nextInt(1,13);     //12 ninde dahil olabilmesi icin 13 e kadar
        if(ay<10) {
            sonKullanmaTarihi = sonKullanmaTarihi.concat("0" + Integer.toString(ay) + "/" + Integer.toString(yil));
        }
        else{
            sonKullanmaTarihi = sonKullanmaTarihi.concat(Integer.toString(ay) + "/" + Integer.toString(yil));

        }
        System.out.println("Kart son kullanma tarihi : " + sonKullanmaTarihi);

        if(flag==0){
            try{

                FileWriter yaz = new FileWriter(dosya,true);
                String veri = "";
                veri = veri.concat(kartNo + ";" + cvv + ";" + sonKullanmaTarihi + ";" + internetAlisverisi + ";");
                yaz.write(veri);
                yaz.close();
            }
            catch (Exception e){
                System.out.println("Dosyaya yazma isleminde hata olustu");
                e.printStackTrace();
            }
        }
    }

    public void internetAlisverisi(File dosya,String isimSoyisim,int satir,int dosyaSatiri) {
        String barcala,bas;
        int index1, index2,bakiye;

        try {
            Scanner oku = new Scanner(dosya);
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                if (isimSoyisim.equals(adSoyad)) {
                    bas = icerik.substring(0, index + 72);      //internet bankaciligi islemleri
                    String temp = icerik.replaceAll(bas, "");
                    index1 = temp.indexOf(";");
                    internetAlisverisi = temp.substring(0, index1);
                    if (internetAlisverisi.equals("kapali")) {
                        barcala = icerik.substring(0, index + 79);      //bakiye islemleri
                        temp = icerik.replaceAll(barcala, "");
                        index2 = temp.indexOf(";");
                        bakiye = Integer.parseInt(temp.substring(0, index2));

                        internetAlisverisi = "acik";
                        String yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                        List<String> dosyaSatirlari = readFile(dosya.getName());
                        int silinecekSatir = satir - 1;
                        dosyaSatirlari.remove(silinecekSatir);
                        dosyaSatirlari.add(yeni);

                        writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                        System.out.println("Kartiniz internet alisverisine acilmistir");
                    }
                    else {
                        barcala = icerik.substring(0, index + 77);      //bakiye islemleri
                        temp = icerik.replaceAll(barcala, "");
                        index2 = temp.indexOf(";");
                        bakiye = Integer.parseInt(temp.substring(0, index2));

                        internetAlisverisi = "kapali";
                        String yeni = bas + internetAlisverisi + ";" + bakiye + ";";

                        List<String> dosyaSatirlari = readFile(dosya.getName());
                        int silinecekSatir = satir - 1;
                        dosyaSatirlari.remove(silinecekSatir);
                        dosyaSatirlari.add(yeni);
                        writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);

                        System.out.println("Kartiniz internet alisverisine kapatilmistir");
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Internet alisverisi guncellemesinde hata olustu");
            e.printStackTrace();
        }
    }


    public void kartBilgileriniListele(File dosya,String isimSoyisim){
        try {
            Scanner oku = new Scanner(dosya);
            String bas,b;
            int index1;
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                if(isimSoyisim.equals(adSoyad)){
                    kartNo =icerik.substring(index+42 , index+61);
                    cvv =icerik.substring(index+62 , index+65);
                    sonKullanmaTarihi =icerik.substring(index+66 , index+71);
                    bas = icerik.substring(0, index + 72);
                    b = icerik.replaceAll(bas, "");
                    index1 = b.indexOf(";");
                    internetAlisverisi = b.substring(0, index1);
                    System.out.println("Kart Numarasi : " + kartNo);
                    System.out.println("CVV : " + cvv);
                    System.out.println("Son Kullanma Tarihi : " + sonKullanmaTarihi);
                    System.out.println("Internet Alisverisine : " + internetAlisverisi);
                }
            }
        }
        catch (Exception e){
            System.out.println("kart bilgileri listelenirken hata olustu");
            e.printStackTrace();
        }
    }

    public static void kartSorgula(File dosya){
        try {
            Scanner oku = new Scanner(dosya);
            int index1;
            Scanner inp = new Scanner(System.in);
            System.out.println("Sorgulamak istediginiz kartin son 6 hanesini giriniz : ");
            String sonAlti = inp.nextLine();
            int bayrak=0;
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                String temp = "";
                String dosyaSonAlti = "";

                String barcala = icerik.substring(0, index + 54);
                String temp2 = icerik.replaceAll(barcala, "");
                index1 = temp2.indexOf(";");
                temp = temp2.substring(0, index1);
                dosyaSonAlti = temp.replace(" ","");

                if(sonAlti.equals(dosyaSonAlti)) {
                    bayrak++;
                    kartNo =icerik.substring(index+42 , index+61);
                    cvv =icerik.substring(index+62 , index+65);
                    sonKullanmaTarihi =icerik.substring(index+66 , index+71);
                    System.out.println("Kart sahibinin adi : " + adSoyad);
                    System.out.println("Kart Numarasi : " + kartNo);
                    System.out.println("Kart cvv'si : " + cvv);
                    System.out.println("Kart son kullanma tarihi : " + sonKullanmaTarihi);
                }
            }
            if(bayrak==0){
                System.out.println("Son 6 hanesi " + sonAlti + " olan bir kart bulunamadi.");
            }
        }
        catch (Exception e){
            System.out.println("Kart sorugulamada hata olustu");
            e.printStackTrace();
        }
    }

    public void kartSil(String isimSoyisim,File dosya,int satir,int dosyaSatiri){
        try {
            Scanner oku = new Scanner(dosya);
            String bas,temp,sonuc;
            int index1;
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                if(isimSoyisim.equals(adSoyad)){
                    bas = icerik.substring(0, index + 72);
                    temp = icerik.replaceAll(bas, "");
                    index1 = temp.indexOf(";");
                    internetAlisverisi = temp.substring(0, index1);
                    if(internetAlisverisi.equals("kapali")){
                        index = icerik.indexOf(";");
                        String sil =icerik.substring(index+42 , index+79);
                        sonuc = icerik.replaceAll(sil,"");

                        List<String> dosyaSatirlari = readFile(dosya.getName());
                        int silinecekSatir = satir - 1;
                        dosyaSatirlari.remove(silinecekSatir);
                        dosyaSatirlari.add(sonuc);

                        writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri);
                        System.out.println("Kartiniz silinmistir");
                    }
                    else {
                        index = icerik.indexOf(";");
                        String sil =icerik.substring(index+42 , index+77);
                        sonuc = icerik.replaceAll(sil,"");

                        List<String> dosyaSatirlari = readFile(dosya.getName());
                        int silinecekSatir = satir - 1;
                        dosyaSatirlari.remove(silinecekSatir);
                        dosyaSatirlari.add(sonuc);

                        writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri);
                        System.out.println("Kartiniz silinmistir");
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("kart silinirken listelenirken hata olustu");
            e.printStackTrace();
        }
    }

    public void kartBilgileriniGuncelle(File dosya, String isimSoyisim,int satir,int dosyaSatiri,int index){
        int secim;
        Scanner inp = new Scanner(System.in);
        String barcala;
        int bakiye;
        int index1,index2;
        try {
            System.out.println("Kartiniz calindi ise 1 ");
            System.out.println("Kartinizin son kullanma tarihini uzatmak icin 2 yaziniz");
            secim = inp.nextInt();
            if (secim == 1) {
                try {
                    Scanner oku = new Scanner(dosya);
                    String bas,temp;
                    while (oku.hasNextLine()) {
                        String icerik = oku.nextLine();//adsoyad okuma ve kontrol etme
                        String adSoyad = icerik.substring(0, index);
                        if(isimSoyisim.equals(adSoyad)){
                            bas = icerik.substring(0, index + 72);
                            temp = icerik.replaceAll(bas, "");
                            index1 = temp.indexOf(";");     //internetalisverisini bulma
                            internetAlisverisi = temp.substring(0, index1);
                            if(internetAlisverisi.equals("kapali")){

                                barcala = icerik.substring(0, index + 79);      //bakiye islemleri
                                temp = icerik.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));
                                int flag = 1;
                                kartOlustur(dosya,flag);
                                String yeniKart = icerik.substring(0,index+42) + kartNo + ";" + cvv + ";" + sonKullanmaTarihi + ";" + internetAlisverisi + ";" + bakiye + ";";

                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                int silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeniKart);

                                writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri);
                                System.out.println("Eski kartiniz silinmiş. Yeni kartiniz olusturulmustur");

                            }
                            else {
                                barcala = icerik.substring(0, index + 77);      //bakiye islemleri
                                temp = icerik.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));
                                int flag = 1;
                                kartOlustur(dosya,flag);
                                String yeniKart = icerik.substring(0,index+42) + kartNo + ";" + cvv + ";" + sonKullanmaTarihi + ";" + internetAlisverisi + ";" + bakiye + ";";

                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                int silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeniKart);

                                writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri);
                                System.out.println("Eski kartiniz silinmiş. Yeni kartiniz olusturulmustur");

                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("kartiniz silinip yeniden olusturulurken hata olustu");
                    e.printStackTrace();
                }


            } else if (secim == 2) {
                try {
                    Random rastgele = new Random();
                    Scanner oku = new Scanner(dosya);
                    String bas,temp;
                    while (oku.hasNextLine()) {
                        String icerik = oku.nextLine();
                        String adSoyad = icerik.substring(0, index);
                        if(isimSoyisim.equals(adSoyad)){
                            bas = icerik.substring(0, index + 72);
                            temp = icerik.replaceAll(bas, "");
                            index1 = temp.indexOf(";");     //internetalisverisini bulma
                            internetAlisverisi = temp.substring(0, index1);
                            if(internetAlisverisi.equals("kapali")){

                                barcala = icerik.substring(0, index + 79);      //bakiye islemleri
                                temp = icerik.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));

                                int enDusukYil =  Integer.parseInt(icerik.substring(index+69,index+71));
                                if(enDusukYil==29){
                                    System.out.println("Son kullanma tarihine daha cok vakit oldugu icin uzatilmadi");
                                }
                                else{
                                    int yil = rastgele.nextInt(enDusukYil, 30);   //son kullanma tarihi olusturma 2023 ile 2029 arasinda
                                    int ay = rastgele.nextInt(1,13);     //12 ninde dahil olabilmesi icin 13 e kadar
                                    if(ay<10) {
                                        sonKullanmaTarihi = sonKullanmaTarihi.concat("0" + Integer.toString(ay) + "/" + Integer.toString(yil));
                                    }
                                    else{
                                        sonKullanmaTarihi = sonKullanmaTarihi.concat(Integer.toString(ay) + "/" + Integer.toString(yil));

                                    }

                                    String yeniKart = icerik.substring(0,index+66) + sonKullanmaTarihi  + ";" + internetAlisverisi + ";" + bakiye + ";";

                                    List<String> dosyaSatirlari = readFile(dosya.getName());
                                    int silinecekSatir = satir - 1;
                                    dosyaSatirlari.remove(silinecekSatir);
                                    dosyaSatirlari.add(yeniKart);

                                    writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri);
                                    System.out.println("Son kullanma tarihiniz uzatilmistir");
                                }


                            }
                            else {
                                barcala = icerik.substring(0, index + 77);      //bakiye islemleri
                                temp = icerik.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));

                                int enDusukYil =  Integer.parseInt(icerik.substring(index+69,index+71));
                                if(enDusukYil==29){
                                    System.out.println("Son kullanma tarihine daha cok vakit oldugu icin uzatilmadi");
                                }
                                else {
                                    int yil = rastgele.nextInt(enDusukYil, 30);   //son kullanma tarihi olusturma 2023 ile 2029 arasindas
                                    int ay = rastgele.nextInt(1,13);     //12 ninde dahil olabilmesi icin 13 e kadar
                                    if(ay<10) {
                                        sonKullanmaTarihi = sonKullanmaTarihi.concat("0" + Integer.toString(ay) + "/" + Integer.toString(yil));
                                    }
                                    else{
                                        sonKullanmaTarihi = sonKullanmaTarihi.concat(Integer.toString(ay) + "/" + Integer.toString(yil));

                                    }

                                    String yeniKart = icerik.substring(0,index+66) + sonKullanmaTarihi  + ";" + internetAlisverisi + ";" + bakiye + ";";

                                    List<String> dosyaSatirlari = readFile(dosya.getName());
                                    int silinecekSatir = satir - 1;
                                    dosyaSatirlari.remove(silinecekSatir);
                                    dosyaSatirlari.add(yeniKart);

                                    writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri);
                                    System.out.println("Son kullanma tarihiniz uzatilmistir");
                                }


                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("kartinizin son kullanma tarihi uzatilirken hata olustu");
                    e.printStackTrace();
                }


            } else {
                System.out.println("Yanlis secim yaptiniz");
            }
        } catch (Exception e) {
            System.out.println("Hatali veri girisi");
            e.printStackTrace();
        }
    }

    public static List<String> readFile(String fileName) {
        List<String> dosyaSatirlari = new ArrayList<>();
        String satirlar;
        try {
            BufferedReader bufferOkuyucu = new BufferedReader(new FileReader(fileName));
            while ((satirlar = bufferOkuyucu.readLine()) != null) {
                dosyaSatirlari.add(satirlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dosyaSatirlari;
    }
/*
    public static void writeFile(String dosyaAdi, List<String> dosyaSatrilari,int dosyaSatiri) {
        try {
            BufferedWriter bufferYazici = new BufferedWriter(new FileWriter(dosyaAdi));
            int i=0;
            if(dosyaSatrilari.size()==dosyaSatiri){
                for (String yazilacakSatir : dosyaSatrilari) {
                    i++;
                    if(i<dosyaSatiri){
                        bufferYazici.write(yazilacakSatir);
                        bufferYazici.newLine();
                    }
                    else if(i==dosyaSatiri){
                        bufferYazici.write(yazilacakSatir);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public static void writeFile(String dosyaAdi, List<String> dosyaSatrilari,int dosyaSatiri) {
        try {
            BufferedWriter bufferYazici = new BufferedWriter(new FileWriter(dosyaAdi));
            int sayac=1;
            for (String yazilacakSatir : dosyaSatrilari) {
                if(dosyaSatrilari.size()<=dosyaSatiri && dosyaSatrilari.size()!=1 && sayac < dosyaSatiri){
                    bufferYazici.write(yazilacakSatir);
                    bufferYazici.newLine();
                    sayac++;
                }
                else if(dosyaSatrilari.size()==1 || sayac == dosyaSatiri){
                    bufferYazici.write(yazilacakSatir);
                }
                else {
                    bufferYazici.write(yazilacakSatir);
                    bufferYazici.newLine();
                }
            }
            bufferYazici.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
