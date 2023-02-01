package BankaProjesi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class ParaTransferi extends KartIslemleri{
    public int aktarilacakTutar;
    public String barcala = "";
    public String bas = "";
    public String internetAlisverisi;
    public int bakiye;
    public int index1,index2;
    public String temp;
    public int silinecekSatir;
    public String yeni;
    public String adSoyad;
    public String icerik;


    public ParaTransferi(){
        aktarilacakTutar=0;
    }

    public void gonder(File dosya,String isimSoyisim,int satir,int dosyaSatiri,int index){
        Scanner inp = new Scanner(System.in);
        System.out.println("Para gondermek istediginiz hesabin adini ve soyadini giriniz : ");
        String gonderilenAd = inp.nextLine();
        System.out.println("Para gondermek istediginiz hesabin ibanini giriniz : ");
        String gonderilenIban = inp.nextLine();
        System.out.println("Gondermek istediginiz tutar : ");
        aktarilacakTutar = inp.nextInt();

        try {
            try {
                Scanner oku = new Scanner(dosya);
                while (oku.hasNextLine()) {
                    icerik = oku.nextLine();
                    adSoyad = icerik.substring(0, index);
                    if (isimSoyisim.equals(adSoyad)) {

                        bas = icerik.substring(0, index + 72);
                        temp = icerik.replaceAll(bas, "");
                        index1 = temp.indexOf(";");
                        internetAlisverisi = temp.substring(0, index1);

                        if(internetAlisverisi.equals("kapali")){

                            barcala = icerik.substring(0, index + 79);
                            temp = icerik.replaceAll(barcala, "");
                            index2 = temp.indexOf(";");
                            bakiye = Integer.parseInt(temp.substring(0, index2));

                            if(bakiye<aktarilacakTutar){
                                System.out.println("bu miktarda hesabinizdan para gonderemezsiniz hesabinizdaki para : " + bakiye);
                            }
                            else {
                                bakiye-=aktarilacakTutar;

                                yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);
                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);

                            }
                        }
                        else {
                            barcala = icerik.substring(0, index + 77);
                            temp = icerik.replaceAll(barcala, "");
                            index2 = temp.indexOf(";");
                            bakiye = Integer.parseInt(temp.substring(0, index2));

                            if(bakiye<aktarilacakTutar){
                                System.out.println("bu miktarda hesabinizdan para gonderemezsiniz hesabinizdaki para : " + bakiye);
                            }
                            else {
                                bakiye-=aktarilacakTutar;

                                yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                silinecekSatir = satir - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);
                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                            }
                        }
                    }
                }

                Scanner oku2 = new Scanner(dosya);
                int satir2 = 0;
                while (oku2.hasNextLine()){
                    String icerik2 = oku2.nextLine();
                    index = icerik2.indexOf(";");
                    String adSoyad = icerik2.substring(0, index);
                    satir2++;
                    if (gonderilenAd.equals(adSoyad)) {
                        index = icerik2.indexOf(";");
                        String iban = icerik2.substring(index+15,index+41);
                        if(iban.equals(gonderilenIban)){
                            bas = icerik2.substring(0, index + 72);
                            temp = icerik2.replaceAll(bas, "");
                            index1 = temp.indexOf(";");
                            internetAlisverisi = temp.substring(0, index1);

                            if(internetAlisverisi.equals("kapali")){

                                barcala = icerik2.substring(0, index + 79);
                                temp = icerik2.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));
                                bakiye+=aktarilacakTutar;

                                yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                silinecekSatir = satir2 - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);

                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                System.out.println("Para transferi basariyla gerceklesmistir");
                            }
                            else {
                                barcala = icerik2.substring(0, index + 77);
                                temp = icerik2.replaceAll(barcala, "");
                                index2 = temp.indexOf(";");
                                bakiye = Integer.parseInt(temp.substring(0, index2));
                                bakiye+=aktarilacakTutar;

                                yeni = bas + internetAlisverisi + ";" + bakiye + ";";
                                List<String> dosyaSatirlari = readFile(dosya.getName());
                                silinecekSatir = satir2 - 1;
                                dosyaSatirlari.remove(silinecekSatir);
                                dosyaSatirlari.add(yeni);

                                writeFile(dosya.getName(), dosyaSatirlari, dosyaSatiri);
                                System.out.println("Para transferi basariyla gerceklesmistir");
                            }
                        }
                        else {
                            System.out.println("Boyle bir iban bulunamadi");
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Para transferinde hata olustu");
                e.printStackTrace();
            }
        }
        catch (Exception e){
            System.out.println("Hatali veri tipi girisi");
            e.printStackTrace();
        }
    }

    public static List<String> readFile(String dosyaAdi) {
        List<String> dosyaSatirlari = new ArrayList<>();
        String satirlar;
        try {
            BufferedReader bufferOkuyucu = new BufferedReader(new FileReader(dosyaAdi));
            while ((satirlar = bufferOkuyucu.readLine()) != null) {
                dosyaSatirlari.add(satirlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dosyaSatirlari;
    }

    public static void writeFile(String dosyaAdi, List<String> dosyaSatrilari, int dosyaSatiri) {
        try  {
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
            bufferYazici.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
