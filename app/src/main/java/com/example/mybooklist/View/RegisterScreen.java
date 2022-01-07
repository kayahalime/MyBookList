package com.example.mybooklist.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mybooklist.Model.Kullanici;
import com.example.mybooklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterScreen extends AppCompatActivity {


    private ProgressDialog mProgress;
    private Kullanici mKullanici;
    private EditText editEmail,editPassword,editUserName,editPasswordTekrar;
    private String textEmail,textPassword,textUserName,textPasswordTekrar;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private HashMap<String,Object> mData;
    private FirebaseFirestore mFirestore;
    private ImageView btn_back;

    private void init(){
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editUserName = findViewById(R.id.userName);
        editPasswordTekrar = findViewById(R.id.password2);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        btn_back = findViewById(R.id.register_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        init();


    }
    public void kayitOl(View view){
        textEmail = editEmail.getText().toString();
        textPassword = editPassword.getText().toString();
        textUserName = editUserName.getText().toString();
        textPasswordTekrar = editPasswordTekrar.getText().toString();

        if(!TextUtils.isEmpty(textUserName)  ){
           if(!TextUtils.isEmpty(textEmail)){
               if(!TextUtils.isEmpty(textPassword)){
                   if(!TextUtils.isEmpty(textPasswordTekrar)){
                       if(textPassword.equals(textPasswordTekrar)){
                           mProgress = new ProgressDialog(this);
                           mProgress.setTitle("Kayıt olunuyor...");
                           mProgress.show();
                           mAuth.createUserWithEmailAndPassword(textEmail,textPassword)
                                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                       @Override
                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                           if(task.isSuccessful()){
                                               mUser = mAuth.getCurrentUser();
                                               if(mUser != null){
                                                   mKullanici= new Kullanici(textUserName,textEmail,mUser.getUid(),"default");


                                                   mFirestore.collection("Kullanıcılar").document(mUser.getUid())
                                                           .set(mKullanici)
                                                           .addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   if(task.isSuccessful()){
                                                                       progressAyar();
                                                                       Toast.makeText(RegisterScreen.this,"Kayıt işlemi başarılı",Toast.LENGTH_SHORT).show();
                                                                       finish();
                                                                       startActivity(new Intent(RegisterScreen.this, MainScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                                   }
                                                                   else{
                                                                       progressAyar();
                                                                       Toast.makeText(RegisterScreen.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                   }

                                                               }
                                                           });
                                               }else{
                                                   progressAyar();
                                               }
                                           }
                                           else{
                                               Toast.makeText(RegisterScreen.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                           }

                                       }
                                   });



                       }else{
                           Toast.makeText(this,"Hata! Şifreler uyuşmuyor.",Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(this,"Hata! Şifrenizi tekrar giriniz.",Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(this,"Hata! Şifrenizi giriniz.",Toast.LENGTH_SHORT).show();
               }
           }else{
               Toast.makeText(this,"Hata! Email adresinizi giriniz.",Toast.LENGTH_SHORT).show();
           }
        }else{
            Toast.makeText(this,"Hata! Kullanıcı adınızı giriniz.",Toast.LENGTH_SHORT).show();
        }

    }
    private void progressAyar(){
        if(mProgress.isShowing()){
            mProgress.dismiss();
        }
    }
}