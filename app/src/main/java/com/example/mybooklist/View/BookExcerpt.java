package com.example.mybooklist.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mybooklist.Adapter.AlintiAdapter;
import com.example.mybooklist.Adapter.KitapAdapter;
import com.example.mybooklist.Fragment.BookFragment;
import com.example.mybooklist.Model.Alinti;
import com.example.mybooklist.Model.Kitap;
import com.example.mybooklist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookExcerpt extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlintiAdapter alintiAdapter;
    private List<Alinti> alintiList;
    private Alinti mAlinti;

    ImageView back_alinti;

    EditText edit_alinti;
    ImageView alinti_camera;
    TextView txt_alinti_gonder;

    String kitapAdi;
    String gonderen_id;
    FirebaseFirestore mFirestore;
    FirebaseUser mevcutKullanici;
    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_excerpt);


        edit_alinti = findViewById(R.id.edit_alinti);
        alinti_camera = findViewById(R.id.alinti_camera);
        txt_alinti_gonder = findViewById(R.id.alinti_gonder);
        back_alinti = findViewById(R.id.back_alinti);

        back_alinti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alinti_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookExcerpt.this,ImageToText.class);
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_alinti_activty);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        alintiList =new ArrayList<>();
        alintiAdapter = new AlintiAdapter(alintiList,this);
        recyclerView.setAdapter(alintiAdapter);

        mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        collectionReference = mFirestore.collection("Alintilar");
        Intent intent = getIntent();
        kitapAdi = intent.getStringExtra("kitapAdi");
        gonderen_id = intent.getStringExtra("kullanici_id");
        txt_alinti_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_alinti.getText().toString().equals("")){
                    Toast.makeText(BookExcerpt.this, "Alanı doldurunuz...", Toast.LENGTH_SHORT).show();

                }
                else{
                        alintiEkle();
                }
            }
        });
        collectionReference.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(BookExcerpt.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(value != null){
                    alintiList.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        mAlinti = snapshot.toObject(Alinti.class);
                        assert  mAlinti != null; // kullanıcı objesinin boş olmadığını kontrol ediyoruz
                        if(mAlinti.getKullanici().equals(mevcutKullanici.getUid())){ // sadece giriş yapan kullanıcıya ait kitapları listelemek istediğimiz için bu kontrolü yapıyoruz
                            if(mAlinti.getKitap().equals(kitapAdi)){
                                alintiList.add(mAlinti);
                            }

                        }
                        recyclerView.addItemDecoration(new LinearDecoration(10,alintiList.size()));
                        alintiAdapter = new AlintiAdapter(alintiList, BookExcerpt.this);
                        recyclerView.setAdapter(alintiAdapter);
                    }
                }

            }
        });




    }

    private void alintiEkle() {

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("alinti",edit_alinti.getText().toString());
        hashMap.put("kullanici",mevcutKullanici.getUid());
        hashMap.put("kitap",kitapAdi);

        mFirestore.collection("Alintilar").document().set(hashMap);
        Toast.makeText(BookExcerpt.this, "Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
        edit_alinti.setText("");
    }
    class LinearDecoration extends RecyclerView.ItemDecoration{
        private int boslukMiktari;
        private int veriSayisi;

        public LinearDecoration(int boslukMiktari, int veriSayisi) {
            this.boslukMiktari = boslukMiktari;
            this.veriSayisi = veriSayisi;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int pos = parent.getChildAdapterPosition(view);
            if(pos != (veriSayisi -1)){
                outRect.bottom = boslukMiktari;
            }
        }
    }


}

