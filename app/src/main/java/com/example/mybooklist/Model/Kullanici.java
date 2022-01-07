package com.example.mybooklist.Model;

import android.widget.ImageView;

public class Kullanici {

    private String kullaniciAdi,kullaniciEmail,kullaniciId,kullaniciProfil;



    public Kullanici( String kullaniciAdi,String kullaniciEmail,String kullaniciId,String kullaniciProfil ) {
        this.kullaniciAdi = kullaniciAdi;
        this.kullaniciEmail = kullaniciEmail;
        this.kullaniciId = kullaniciId;
        this.kullaniciProfil=kullaniciProfil;

    }

    public Kullanici() {
    }



    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }
    public String getKullaniciEmail() {
        return kullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }

    public String getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getKullaniciProfil() {
        return kullaniciProfil;
    }
}
