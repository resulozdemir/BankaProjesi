package BankaProjesi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

public class HesapIslemleri {
    private static String Iban;
    private static String hesapNo;
    protected int bakiye;

    public HesapIslemleri() {
        Iban = "TR90";
        hesapNo = "";
        bakiye = 0;
    }

    public void HesapOlustur(File dosya) {
        Random rastgele = new Random();
        int temp;
        temp = rastgele.nextInt(1, 10);
        hesapNo = hesapNo.concat(Integer.toString(temp));
        for (int i = 0; i < 7; i++) {
            temp = rastgele.nextInt(10);
            hesapNo = hesapNo.concat(Integer.toString(temp));

        }
        System.out.println("Hesap Numarasi : " + hesapNo);

        for (int i = 0; i < 14; i++) {
            temp = rastgele.nextInt(10);
            Iban = Iban.concat(Integer.toString(temp));
        }
        Iban = Iban.concat(hesapNo);
        System.out.println("Iban : " + Iban);

        try {

            FileWriter yaz = new FileWriter(dosya, true);
            String veri = "";
            veri = veri.concat(hesapNo + ";" + Iban + ";");
            yaz.write(veri);
            yaz.close();
        } catch (Exception e) {
            System.out.println("Dosyaya yazma isleminde hata olustu");
            e.printStackTrace();
        }
    }

    public void dosyayaBakiyeYazdir(File dosya) {
        try {
            FileWriter yaz = new FileWriter(dosya, true);
            String veri = "";
            veri = veri.concat(bakiye + ";");
            yaz.write(veri);
            yaz.close();
        } catch (Exception e) {
            System.out.println("Dosyaya yazma isleminde hata olustu");
            e.printStackTrace();
        }
    }

    public void hesapBilgileriniGuncelle(File dosya, String isimSoyisim,int satir,int dosyaSatiri,String sifre) {
        int secim;
        Scanner inp = new Scanner(System.in);
        String bas,internetAlisverisi,barcala,degisecekAd,temp;
        int index1,index2;
        try {
            Scanner oku = new Scanner(dosya);

            System.out.println("Ad Soyad bilgilerinizi guncellemek icin 1'e ");
            System.out.println("Sifre bilgilerinizi guncellemek icin 2 yaziniz");
            secim = inp.nextInt();
            if (secim == 1) {
                try {
                    while (oku.hasNextLine()) {
                        String icerik = oku.nextLine();
                        int index = icerik.indexOf(";");
                        String adSoyadDisindakiler = icerik.substring(index,index+71);
                        String adSoyad = icerik.substring(0, index);
                        if(isimSoyisim.equals(adSoyad)){
                            Scanner input = new Scanner(System.in);
                            System.out.println("Yeni adinizi ve soyadinizi giriniz : ");
                            degisecekAd = input.nextLine();

                            if(adSoyad.equals(degisecekAd)){
                                System.out.println("Yeni isminizle eski isminiz ayni");
                            }
                            else {
                                bas = icerik.substring(0, index + 72);
                                temp = icerik.replaceAll(bas, "");
                                index1 = temp.indexOf(";");
                                internetAlisverisi = temp.substring(0, index1);

                                if(internetAlisverisi.equals("kapali")){
                                    barcala = icerik.substring(0, index + 79);
                                    temp = icerik.replaceAll(barcala, "");
                                    index2 = temp.indexOf(";");
                                    bakiye = Integer.parseInt(temp.substring(0, index2));

                                    String yeni = degisecekAd + adSoyadDisindakiler + ";" + internetAlisverisi + ";" + bakiye + ";";
                                    List<String> dosyaSatirlari = readFile(dosya.getName());
                                    int silinecekSatir = satir - 1;
                                    dosyaSatirlari.remove(silinecekSatir);
                                    dosyaSatirlari.add(yeni);

                                    writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);

                                    System.out.println("adiniz ve soyadiniz basariyla degistirilmistir");
                                }
                                else {
                                    barcala = icerik.substring(0, index + 77);
                                    temp = icerik.replaceAll(barcala, "");
                                    index2 = temp.indexOf(";");
                                    bakiye = Integer.parseInt(temp.substring(0, index2));

                                    String yeni = degisecekAd + adSoyadDisindakiler + ";" + internetAlisverisi + ";" + bakiye + ";";
                                    List<String> dosyaSatirlari = readFile(dosya.getName());
                                    int silinecekSatir = satir - 1;
                                    dosyaSatirlari.remove(silinecekSatir);
                                    dosyaSatirlari.add(yeni);

                                    writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);

                                    System.out.println("adiniz ve soyadiniz basariyla degistirilmistir");
                                }
                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("hesap bilgileri degistirilirken hata olustu");
                    e.printStackTrace();
                }


            } else if (secim == 2) {
                try {
                    while (oku.hasNextLine()) {
                        String icerik = oku.nextLine();
                        int index = icerik.indexOf(";");

                        String sifreDisindakiler = icerik.substring(index+6,index+71);
                        String adSoyad = icerik.substring(0, index);

                        if(isimSoyisim.equals(adSoyad)){
                            Scanner input = new Scanner(System.in);
                            System.out.println("Yeni sifrenizi giriniz : ");
                            String degisecekSifre = input.nextLine();

                            if(sifre.equals(degisecekSifre)){
                                System.out.println("Yeni sifreniz eski sifreniz ile ayni");
                            }
                            else {
                                bas = icerik.substring(0, index + 72);      //internet aliverisi islemleri
                                temp = icerik.replaceAll(bas, "");
                                index1 = temp.indexOf(";");
                                internetAlisverisi = temp.substring(0, index1);

                                if(internetAlisverisi.equals("kapali")){
                                    barcala = icerik.substring(0, index + 79);      //bakiye islemleri
                                    temp = icerik.replaceAll(barcala, "");
                                    index2 = temp.indexOf(";");
                                    bakiye = Integer.parseInt(temp.substring(0, index2));

                                    String yeni = adSoyad + ";"  + degisecekSifre + ";" + sifreDisindakiler + ";" + internetAlisverisi + ";" + bakiye + ";";
                                    List<String> dosyaSatirlari = readFile(dosya.getName());
                                    int silinecekSatir = satir - 1;
                                    dosyaSatirlari.remove(silinecekSatir);
                                    dosyaSatirlari.add(yeni);

                                    writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);

                                    System.out.println("sifreniz basariyla degistirilmistir");
                                }
                                else {
                                    barcala = icerik.substring(0, index + 77);      //bakiye islemleri
                                    temp = icerik.replaceAll(barcala, "");
                                    index2 = temp.indexOf(";");
                                    bakiye = Integer.parseInt(temp.substring(0, index2));

                                    String yeni = adSoyad + ";"  + degisecekSifre + ";" + sifreDisindakiler + ";" + internetAlisverisi + ";" + bakiye + ";";
                                    List<String> dosyaSatirlari = readFile(dosya.getName());
                                    int silinecekSatir = satir - 1;
                                    dosyaSatirlari.remove(silinecekSatir);
                                    dosyaSatirlari.add(yeni);

                                    writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                    System.out.println("sifreniz basariyla degistirilmistir");
                                }
                            }

                        }
                    }
                }
                catch (Exception e){
                    System.out.println("hesap bilgileri degistirilirken hata olustu");
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

    public static void hesapSorgulama(File dosya) {
        Scanner inp = new Scanner(System.in);
        String sonUc,icerik,adSoyad,dosyaSonUc;
        int index;
        try {
            Scanner oku = new Scanner(dosya);
            System.out.println("Sorgulamak istediginiz hesap numarasinin son 3 hanesini giriniz : ");
            sonUc = inp.nextLine();
            while (oku.hasNextLine()) {
                icerik = oku.nextLine();
                index = icerik.indexOf(";");
                adSoyad = icerik.substring(0, index);
                dosyaSonUc = icerik.substring(index+11, index+14);

                if (sonUc.equals(dosyaSonUc)) {
                    hesapNo = icerik.substring(index + 6, index + 14);
                    Iban = icerik.substring(index + 15, index + 41);
                    System.out.println("Hesap sahibinin adi : " + adSoyad);
                    System.out.println("Hesap Numarasi : " + hesapNo);
                    System.out.println("Hesap Ibani : " + Iban);
                }
            }
        } catch (Exception e) {
            System.out.println("Bakiye sorugulamada hata olustu");
            e.printStackTrace();
        }
    }

    public void hesapListeleme(String isimSoyisim, File dosya) {

        try {
            Scanner oku = new Scanner(dosya);
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                if (isimSoyisim.equals(adSoyad)) {
                    index = icerik.indexOf(";");
                    hesapNo = icerik.substring(index + 6, index + 14);
                    Iban = icerik.substring(index + 15, index + 41);
                    System.out.println("Hesap No : " + hesapNo);
                    System.out.println("Iban : " + Iban);
                    bakiyeSorgulama(dosya, isimSoyisim);
                }
            }
        } catch (Exception e) {
            System.out.println("kart bilgileri listelenirken hata olustu");
            e.printStackTrace();
        }
    }

    public void bakiyeSorgulama(File dosya, String isimSoyisim) {
        int index1,index2;
        String bas,temp,internetAlisverisi;
        try {
            Scanner oku = new Scanner(dosya);
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                if (isimSoyisim.equals(adSoyad)) {

                    bas = icerik.substring(0, index + 72);
                    temp = icerik.replaceAll(bas, "");
                    index1 = temp.indexOf(";");
                    internetAlisverisi = temp.substring(0, index1);

                    if(internetAlisverisi.equals("kapali")){
                        String barcala = adSoyad + icerik.substring(index , index + 79);
                        temp = icerik.replaceAll(barcala, "");
                        index2 = temp.indexOf(";");
                        bakiye = Integer.parseInt(temp.substring(0, index2));
                        System.out.println("Hesabinizdaki bakiye : " + bakiye);


                    }
                    else{
                        String barcala = adSoyad + icerik.substring(index , index + 77);
                        temp = icerik.replaceAll(barcala, "");
                        index2 = temp.indexOf(";");
                        bakiye = Integer.parseInt(temp.substring(0, index2));
                        System.out.println("Hesabinizdaki bakiye : " + bakiye);
                    }


                }
            }
        } catch (Exception e) {
            System.out.println("Bakiye sorugulamada hata olustu");
            e.printStackTrace();
        }
    }

    public void hesapSil(File dosya, String isimSoyisim, int satir,int dosyaSatiri) {
        try {
            Scanner oku = new Scanner(dosya);
            while (oku.hasNextLine()) {
                String icerik = oku.nextLine();
                int index = icerik.indexOf(";");
                String adSoyad = icerik.substring(0, index);
                if (adSoyad.equals(isimSoyisim)) {
                    List<String> dosyaSatirlari = readFile(dosya.getName());
                    int silinecekSatir = satir - 1;
                    dosyaSatirlari.remove(silinecekSatir);

                    writeFile(dosya.getName(), dosyaSatirlari,dosyaSatiri-1);
                    System.out.println("Hesap basariyla silindi");
                }
            }


        } catch (Exception e) {
            System.out.println("Hesap silme işleminde hata olsutu");
            e.printStackTrace();
        }
    }

    public void paraYatir(File dosya,String isimSoyisim,int satir,int dosyaSatiri) {
        Scanner inp = new Scanner(System.in);
        int index1,index2;
        try {
            String barcala = "";
            String bas = "";
            String internetAlisverisi;
            System.out.println("Hesaba yatirmak istediginiz tutar : ");
            int tutar = inp.nextInt();
                try {
                    Scanner oku = new Scanner(dosya);

                    while (oku.hasNextLine()) {
                        String icerik = oku.nextLine();
                        int index = icerik.indexOf(";");
                        String adSoyad = icerik.substring(0, index);
                        if (isimSoyisim.equals(adSoyad)) {

                            bas = icerik.substring(0, index + 72);
                            String temp = icerik.replaceAll(bas, "");
                            index1 = temp.indexOf(";");
                            internetAlisverisi = temp.substring(0, index1);

                            if(internetAlisverisi.equals("kapali")){

                                barcala = icerik.substring(0, index + 79);
                                temp = icerik.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));
                                bakiye+=tutar;

                                String yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                int silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);

                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                System.out.println("Hesabiniza " + tutar + " yatirilmistir. Hesabinizdaki toplam bakiye : " + bakiye);
                            }
                            else {
                                barcala = icerik.substring(0, index + 77);
                                temp = icerik.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));
                                bakiye+=tutar;

                                String yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                int silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);

                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                System.out.println("Hesabiniza " + tutar + " yatirilmistir. Hesabinizdaki toplam bakiye : " + bakiye);
                            }


                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("para yatirirken hata olustu");
                    e.printStackTrace();
                }

        }
        catch (Exception e){
            System.out.println("Hatali veri tipi girisi");
            e.printStackTrace();
        }
    }


    public void paraCek(File dosya,String isimSoyisim,int satir,int dosyaSatiri){
        Scanner inp = new Scanner(System.in);
        try {
            String barcala = "";
            String bas = "";
            String internetAlisverisi;
            int index1,index2;
            System.out.println("Hesabinizdan cekmek istediginiz tutar : ");
            int tutar = inp.nextInt();
            try {
                Scanner oku = new Scanner(dosya);

                while (oku.hasNextLine()) {
                    String icerik = oku.nextLine();
                    int index = icerik.indexOf(";");
                    String adSoyad = icerik.substring(0, index);
                    if (isimSoyisim.equals(adSoyad)) {

                        bas = icerik.substring(0, index + 72);
                        String temp = icerik.replaceAll(bas, "");
                        index1 = temp.indexOf(";");
                        internetAlisverisi = temp.substring(0, index1);

                        if(internetAlisverisi.equals("kapali")){

                            barcala = icerik.substring(0, index + 79);
                            temp = icerik.replaceAll(barcala, "");
                            index2 = temp.indexOf(";");
                            bakiye = Integer.parseInt(temp.substring(0, index2));

                            if(bakiye<tutar){
                                System.out.println("bu miktarda hesabinizdan para cekemezsiniz hesabinizdaki para : " + bakiye);
                            }
                            else {
                                bakiye-=tutar;

                                String yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                int silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);

                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                System.out.println("Hesabinizdan " + tutar + " cekilmistir. Hesabinizdaki toplam bakiye : " + bakiye);
                            }
                        }
                        else {
                            barcala = icerik.substring(0, index + 77);
                            temp = icerik.replaceAll(barcala, "");
                            index2 = temp.indexOf(";");
                            bakiye = Integer.parseInt(temp.substring(0, index2));

                            if(bakiye<tutar){
                                System.out.println("bu miktarda hesabinizdan para cekemezsiniz hesabinizdaki para : " + bakiye);
                            }
                            else {
                                bakiye-=tutar;

                                String yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                int silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);

                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                System.out.println("Hesabinizdan " + tutar + " cekilmistir. Hesabinizdaki toplam bakiye : " + bakiye);
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Para cekerken hata olustu");
                e.printStackTrace();
            }

        }
        catch (Exception e){
            System.out.println("Hatali veri tipi girisi");
            e.printStackTrace();
        }
    }

    public List<String> readFile(String dosyaAdi) {
        List<String> dosyaSatirlari = new ArrayList<>();
        String satirlar;
        try{
            BufferedReader bufferOkuyucu = new BufferedReader(new FileReader(dosyaAdi));
            while ((satirlar = bufferOkuyucu.readLine()) != null) {
                dosyaSatirlari.add(satirlar);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dosyaSatirlari;
    }

    public void writeFile(String dosyaAdi, List<String> dosyaSatrilari,int dosyaSatiri) {
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

