package com.example.mybooklist.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybooklist.R;
import com.example.mybooklist.View.LoginScreen;
import com.example.mybooklist.View.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class AddFragment extends Fragment {

    Uri resimUri;
    String benimUrim;

    StorageTask yuklemeGorevi;
    StorageReference yuklemeReferans;

    ImageView image_Eklendi;
    TextView txt_Ekle;
    EditText isim,yazar,kategori,sayfaSayisi,hakkinda;
    private FirebaseFirestore mFirestore;
    private CollectionReference collectionReference;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_add, container, false);

        image_Eklendi= v.findViewById(R.id.eklenen_Resim);
        txt_Ekle = v.findViewById(R.id.txt_Gonder);
        isim = v.findViewById(R.id.book_name);
        yazar = v.findViewById(R.id.book_author);
        hakkinda = v.findViewById(R.id.book_about);
        kategori = v.findViewById(R.id.book_category);
        sayfaSayisi = v.findViewById(R.id.page_number);
        mFirestore = FirebaseFirestore.getInstance();
        collectionReference = mFirestore.collection("Kitaplar");


        image_Eklendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yuklemeReferans = FirebaseStorage.getInstance().getReference("kitaplar");


                txt_Ekle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        resimYukle();
                    }
                });
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(getActivity(), AddFragment.this);

            }
        });

        return v;
    }

    private void resimYukle() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Yükleniyor");
        progressDialog.show();

        if(resimUri != null){
            StorageReference dosyaYolu = yuklemeReferans.child(System.currentTimeMillis()
                    +"."+dosyaUzantisiAl(resimUri));

            yuklemeGorevi = dosyaYolu.putFile(resimUri);
            yuklemeGorevi.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }


                    return dosyaYolu.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri indirmeUri = task.getResult();
                        benimUrim = indirmeUri.toString();



                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("kitapResmi",benimUrim);
                        hashMap.put("kitapAdi",isim.getText().toString());
                        hashMap.put("kitapYazari",yazar.getText().toString());
                        hashMap.put("kitapTuru",kategori.getText().toString());
                        hashMap.put("kitapKonusu",hakkinda.getText().toString());
                        hashMap.put("sayfaSayisi",sayfaSayisi.getText().toString());
                        hashMap.put("ekleyen", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        mFirestore.collection("Kitaplar").document().set(hashMap);


                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Başarıyla Eklendi", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getActivity() , "Kitap Eklenemedi!", Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity() , ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getActivity() , "Seçilen Resim Yok", Toast.LENGTH_SHORT).show();
        }
    }

    private String dosyaUzantisiAl(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resimUri = result.getUri();
            image_Eklendi.setImageURI(resimUri);
        }
        else{
            Toast.makeText(getActivity() , "Resim Seçilemedi!", Toast.LENGTH_SHORT).show();

        }
    }


}