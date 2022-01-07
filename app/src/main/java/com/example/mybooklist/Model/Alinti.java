package com.example.mybooklist.Model;

public class Alinti {
    private String alinti;
    private String kitap;
    private String kullanici;

    public Alinti() {
    }

    public Alinti(String alinti, String kitap, String kullanici) {
        this.alinti = alinti;
        this.kitap = kitap;
        this.kullanici = kullanici;
    }

    public String getAlinti() {
        return alinti;
    }

    public void setAlinti(String alinti) {
        this.alinti = alinti;
    }

    public String getKitap() {
        return kitap;
    }

    public void setKitap(String kitap) {
        this.kitap = kitap;
    }

    public String getKullanici() {
        return kullanici;
    }

    public void setKullanici(String kullanici) {
        this.kullanici = kullanici;
    }
}
