package BankaProjesi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Project {
    public static void main(String[] args){
        try {
            File dosya = new File("kullaniciBilgileri.txt");
            Scanner inp = new Scanner(System.in);

            int secim;

            System.out.println("***OZDEMIR BANKASINA HOS GELDINIZ***");
            System.out.println("Kart sorgulamak icin 1");
            System.out.println("Hesap sorgulamak icin 2");
            System.out.println("Musteri giris veya musteri kaydi icin 3 yaziniz : ");

            secim = inp.nextInt();
            if(secim==1){
                KartIslemleri.kartSorgula(dosya);
            }
            else if(secim==2){
                HesapIslemleri.hesapSorgulama(dosya);
            }
            else if(secim==3){
                anaMenu();
            }
            else {
                System.out.println("Aralik disinda deger girisi");
            }
        }
        catch (Exception e){
            System.out.println("Hata olustu");
            e.printStackTrace();
        }
    }

        public static void kullaniciGiris(){
            Scanner inp = new Scanner(System.in);
            File dosya = new File("kullaniciBilgileri.txt");
            int bayrak=0,bayrak2=0,satir=0,secim,islem,index,dosyaSatir=0;
            String isimSoyisim,sifre,adSoyad,parola,icerik;
            KartIslemleri kart = new KartIslemleri();
            HesapIslemleri hesap = new HesapIslemleri();
            ParaTransferi transfer = new ParaTransferi();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(dosya));
                while (reader.readLine() != null) {
                    dosyaSatir++;
                }
            }
            catch (Exception e){
                System.out.println("dosya okunurken hata olustu");
            }
            try {
                if(dosya.createNewFile()){
                    System.out.println("basarili bir sekilde olusturuldu");
                }
                else if(dosya.exists()){
                    try {
                        System.out.println("Adinizi soyadinizi giriniz : ");
                        isimSoyisim = inp.nextLine();
                        System.out.println("Sifrenizi giriniz : ");
                        sifre = inp.nextLine();
                        try {
                            Scanner oku = new Scanner(dosya);
                            while(oku.hasNextLine()){
                                icerik = oku.nextLine();
                                index = icerik.indexOf(";");
                                adSoyad = icerik.substring(0, index);
                                parola = icerik.substring(index+1, index+5);
                                satir++;
                                if(isimSoyisim.equals(adSoyad) && sifre.equals(parola)){
                                    try{
                                        System.out.println("Hosgeldiniz " + adSoyad + "\n");
                                        while (bayrak2==0){
                                            System.out.println("Kart islemleri icin 1");
                                            System.out.println("Hesap islemleri icin 2");
                                            System.out.println("Para transferi islemleri icin 3 yaziniz : ");
                                            secim = inp.nextInt();
                                            if(secim==1){
                                                try {
                                                    System.out.println("Kart Bilgileriniz icin 1");
                                                    System.out.println("Kart Bilgilerinizi guncellemek icin 2");
                                                    System.out.println("Kartinizi internet alisverisine acmak veya kapatmak icin 3");
                                                    System.out.println("Kartinizi silmek icin 4 yazin : ");
                                                    islem = inp.nextInt();

                                                    if(islem==1){
                                                        kart.kartBilgileriniListele(dosya,isimSoyisim);
                                                        bayrak2++;
                                                    }
                                                    else if (islem==2)
                                                    {
                                                        kart.kartBilgileriniGuncelle(dosya,isimSoyisim,satir,dosyaSatir,index);
                                                        bayrak2++;
                                                    }
                                                    else if (islem==3)
                                                    {
                                                        kart.internetAlisverisi(dosya,isimSoyisim,satir,dosyaSatir);
                                                        bayrak2++;
                                                    }
                                                    else if (islem==4)
                                                    {
                                                        kart.kartSil(isimSoyisim,dosya,satir,dosyaSatir);
                                                        bayrak2++;
                                                    }
                                                    else {
                                                        System.out.println("Aralik disinda deger girildi");
                                                    }
                                                }
                                                catch (Exception e){
                                                    System.out.println("Hatali veri girisi");
                                                    e.printStackTrace();
                                                }
                                            }
                                            else if (secim == 2){
                                                try {
                                                    System.out.println("Hesap bilgilerinizi guncellemek icin 1");
                                                    System.out.println("Hesap bilgilerinizi listelemek icin 2 ");
                                                    System.out.println("Hesap bakiyenizi ogrenmek icin 3");
                                                    System.out.println("Para yatirmak icin 4");
                                                    System.out.println("Para cekmek icin 5");
                                                    System.out.println("Hesabinizi silmek icin 6 yaziniz : ");
                                                    islem = inp.nextInt();
                                                    if(islem==1){
                                                        hesap.hesapBilgileriniGuncelle(dosya,isimSoyisim,satir,dosyaSatir,parola);
                                                        bayrak2++;
                                                    }
                                                    else if(islem==2){
                                                        hesap.hesapListeleme(adSoyad,dosya);
                                                        bayrak2++;
                                                    }
                                                    else if(islem==3){
                                                        hesap.bakiyeSorgulama(dosya,isimSoyisim);
                                                        bayrak2++;
                                                    }
                                                    else if(islem==4){
                                                        hesap.paraYatir(dosya,isimSoyisim,satir,dosyaSatir);
                                                        bayrak2++;
                                                    }
                                                    else if(islem==5){
                                                        hesap.paraCek(dosya,isimSoyisim,satir,dosyaSatir);
                                                        bayrak2++;
                                                    }
                                                    else if(islem==6) {
                                                        hesap.hesapSil(dosya,isimSoyisim,satir,dosyaSatir);
                                                        bayrak2++;
                                                    }
                                                    else {
                                                        System.out.println("Aralik disinda deger girisi");
                                                    }
                                                } catch (Exception e){
                                                    System.out.println("Hatali veri girisi");
                                                    e.printStackTrace();
                                                }
                                            }
                                            else if(secim == 3){
                                                transfer.gonder(dosya,isimSoyisim,satir,dosyaSatir,index);
                                                bayrak2++;
                                            }
                                            else {
                                                System.out.println("Aralik disinda deger girisi yapildi");
                                            }
                                        }

                                    }
                                    catch (Exception e){
                                        System.out.println("Hatali veri girisi");
                                        e.printStackTrace();
                                    }
                                    bayrak++;
                                    oku.close();
                                    break;
                                }
                            }
                            if(bayrak==0){
                                try {
                                    System.out.println("Giris yapilamadi");
                                    System.out.println("Kullanici girisi icin 1 ana menu icin 2 yazin : ");
                                    secim = inp.nextInt();
                                    if(secim==1){
                                        kullaniciGiris();
                                    }
                                    else {
                                        anaMenu();
                                    }
                                }
                                catch (Exception e){
                                    System.out.println("Hatali veri girisi");
                                }
                            }
                        }
                        catch (Exception e){
                            System.out.println("Dosya okunurken hata ile karsilasildi");
                            e.printStackTrace();
                        }
                    }
                    catch (Exception e){
                        System.out.println("Hatali veri girisi");
                    }
                }
            }
            catch (Exception e){
                System.out.println("Dosya olusturulurken hata ile karsilasildi");
                e.printStackTrace();
            }
        }

        public static void kayitAlani(){
            Scanner inp = new Scanner(System.in);
            File dosya = new File("kullaniciBilgileri.txt");
            String isimSoyisim,sifre,sifreTekrar,veri;
            int basamak,sayac = 0,bayrak=0,secim;
            HesapIslemleri hesap = new HesapIslemleri();
            KartIslemleri kart = new KartIslemleri();

            try {
                if(dosya.createNewFile()){
                    System.out.println("basarili bir sekilde olusturuldu");
                }
                else if(dosya.exists()){
                    try {
                        System.out.println("Adinizi ve Soyadinizi giriniz : ");
                        isimSoyisim = inp.nextLine();
                        System.out.println("Sifreniniz giriniz : ");
                        sifre = inp.nextLine();
                        System.out.println("Sifrenizi tekrardan giriniz : ");
                        sifreTekrar = inp.nextLine();

                        basamak=Integer.parseInt(sifre);
                        while(basamak > 0) {
                            basamak /= 10;
                            sayac++;
                        }
                        if (sayac == 4){
                            if(sifreTekrar.equals(sifre)){
                                try{
                                    FileWriter yaz = new FileWriter(dosya,true);
                                    veri = "";
                                    veri = veri.concat(isimSoyisim + ";" + sifre + ";");
                                    yaz.write("\r\n");
                                    yaz.write(veri);
                                    System.out.println("Kayit basarili bir sekilde olusturldu");
                                    yaz.close();
                                }
                                catch (Exception e){
                                    System.out.println("Yazma sirasinda bir hata olustu");
                                    e.printStackTrace();
                                }
                                hesap.HesapOlustur(dosya);
                                kart.kartOlustur(dosya,bayrak);
                                hesap.dosyayaBakiyeYazdir(dosya);
                                anaMenu();
                            }
                            else {
                                try {
                                    System.out.println("Kayit olusturulamadi sifrenizi yanlis girdiniz");
                                    System.out.println("Kayit olmak icin 1 ana menu icin 2 yazin : ");
                                    secim = inp.nextInt();
                                    if(secim==1){
                                        kayitAlani();
                                    }
                                    else {
                                        anaMenu();
                                    }
                                }
                                catch (Exception e){
                                    System.out.println("Hatali veri girisi");
                                }
                            }
                        }
                        else {
                            System.out.println("girdiginiz sifre 4 haneli degil");
                            kayitAlani();
                        }
                    }
                    catch (Exception e){
                        System.out.println("Hatali veri girisi");
                    }
                }

            }
            catch (Exception e){
                System.out.println("Dosya olusturulurken hata ile karsilasildi");
                e.printStackTrace();
            }
        }

        public static void anaMenu() {
            Scanner inp = new Scanner(System.in);
            int secim;

            try {
                System.out.println("*Kullanici girisi icin 1");
                System.out.println("*Yeni kayit icin 2 yaziniz :");
                secim = inp.nextInt();

                if(secim==1){
                    kullaniciGiris();
                }
                else if(secim==2) {
                    kayitAlani();
                }
                else {
                    System.out.println("Aralik disinda deger girisi gerceklesti");
                }
            }
            catch (Exception e){
                System.out.println("Hatali veri tipi girisi");
            }
        }

}


