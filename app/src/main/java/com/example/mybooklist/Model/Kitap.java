package com.example.mybooklist.Model;

public class Kitap {
    private String ekleyen;
    private String kitapAdi;
    private String kitapKonusu;
    private String kitapResmi;
    private String kitapTuru;
    private String kitapYazari;
    private String sayfaSayisi;

    public Kitap() {
    }

    public Kitap(String ekleyen, String kitapAdi, String kitapKonusu, String kitapResmi, String kitapTuru, String kitapYazari, String sayfaSayisi) {
        this.ekleyen = ekleyen;
        this.kitapAdi = kitapAdi;
        this.kitapKonusu = kitapKonusu;
        this.kitapResmi = kitapResmi;
        this.kitapTuru = kitapTuru;
        this.kitapYazari = kitapYazari;
        this.sayfaSayisi = sayfaSayisi;
    }

    public String getEkleyen() {
        return ekleyen;
    }

    public void setEkleyen(String ekleyen) {
        this.ekleyen = ekleyen;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getKitapKonusu() {
        return kitapKonusu;
    }

    public void setKitapKonusu(String kitapKonusu) {
        this.kitapKonusu = kitapKonusu;
    }

    public String getKitapResmi() {
        return kitapResmi;
    }

    public void setKitapResmi(String kitapResmi) {
        this.kitapResmi = kitapResmi;
    }

    public String getKitapTuru() {
        return kitapTuru;
    }

    public void setKitapTuru(String kitapTuru) {
        this.kitapTuru = kitapTuru;
    }

    public String getKitapYazari() {
        return kitapYazari;
    }

    public void setKitapYazari(String kitapYazari) {
        this.kitapYazari = kitapYazari;
    }

    public String getSayfaSayisi() {
        return sayfaSayisi;
    }

    public void setSayfaSayisi(String sayfaSayisi) {
        this.sayfaSayisi = sayfaSayisi;
    }
}
