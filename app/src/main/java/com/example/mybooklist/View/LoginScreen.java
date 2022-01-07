package com.example.mybooklist.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybooklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private String textEmail, textPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    int x =0;

    private void init(){
        editEmail = findViewById(R.id.loginEmail);
        editPassword = findViewById(R.id.loginPassword);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        init();
        Intent intent =getIntent();
        x = intent.getIntExtra("keyInt",x);
        if(x ==1){

            mAuth.signOut();
            signOutUser();

        }


        if(mUser != null){
                finish();
                startActivity(new Intent(LoginScreen.this,MainScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        }

    }

    private void signOutUser() {
        Intent in = new Intent(LoginScreen.this,MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();
    }

    public void login(View view) {

        textEmail = editEmail.getText().toString();
        textPassword = editPassword.getText().toString();

        if (!TextUtils.isEmpty(textEmail) && !TextUtils.isEmpty(textPassword)) {
            mAuth.signInWithEmailAndPassword(textEmail, textPassword)
                    .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginScreen.this, "Başarıyla giriş yaptınız.",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(LoginScreen.this,MainScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            }else{
                                Toast.makeText(LoginScreen.this, "Email ve parolanızı kontrol ediniz.",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }else{
            Toast.makeText(LoginScreen.this, "Email ve parolanızı kontrol ediniz.",Toast.LENGTH_SHORT).show();
        }
    }
    public void register(View view){
        Intent i = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(i);
    }
}