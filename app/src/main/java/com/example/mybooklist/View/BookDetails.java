package com.example.mybooklist.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybooklist.Adapter.KitapAdapter;
import com.example.mybooklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookDetails extends AppCompatActivity {

    ImageView detail_back,detail_image;
    TextView detail_kitap_adi,detail_yazar_adi,detail_kitap_turu,detail_sayfa_sayisi,detail_kitap_hakkinda;
    Button btn_alinti;
    FirebaseFirestore mFireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        detail_back = findViewById(R.id.detail_back);
        detail_image = findViewById(R.id.detail_image);
        detail_kitap_adi = findViewById(R.id.detail_kitap_adi);
        detail_yazar_adi = findViewById(R.id.detail_yazar_adi);
        detail_kitap_turu = findViewById(R.id.detail_kitap_turu);
        detail_sayfa_sayisi = findViewById(R.id.detail_sayfa_sayisi);
        detail_kitap_hakkinda = findViewById(R.id.detail_kitap_hakkinda);
        btn_alinti = findViewById(R.id.alinti_ekle_bookDetails);




        Intent i = getIntent();
        String kitapResmi = i.getStringExtra("key_kitap_resmi");
        String kitapAdi = i.getStringExtra("key_kitap_adi");
        String yazarAdi = i.getStringExtra("key_yazar_adi");
        String kitapTuru = i.getStringExtra("key_kitap_turu");
        String sayfaSayisi = i.getStringExtra("key_sayfa_sayisi");
        String kitapHakkinda = i.getStringExtra("key_kitap_konusu");
        String kullaniciId = i.getStringExtra("key_kullanici_id");


        detail_image.setImageURI(Uri.parse(kitapResmi));
        detail_kitap_adi.setText(kitapAdi);
        detail_yazar_adi.setText(yazarAdi);
        detail_kitap_turu.setText(kitapTuru);
        detail_sayfa_sayisi.setText(sayfaSayisi);
        detail_kitap_hakkinda.setText(kitapHakkinda);
        Picasso.get().load(kitapResmi).resize(500,500).into(detail_image);


        detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetails.this,MainScreen.class);
                startActivity(intent);
            }
        });
        btn_alinti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetails.this,BookExcerpt.class);
                intent.putExtra("kitapAdi",kitapAdi);
                intent.putExtra("kullanici_id",kullaniciId);
                startActivity(intent);
            }
        });

    }

}