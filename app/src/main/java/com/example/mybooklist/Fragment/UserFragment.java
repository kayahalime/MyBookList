package com.example.mybooklist.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybooklist.Model.Kullanici;
import com.example.mybooklist.R;
import com.example.mybooklist.View.BookDetails;
import com.example.mybooklist.View.LoginScreen;
import com.example.mybooklist.View.MainActivity;
import com.example.mybooklist.View.MainScreen;
import com.example.mybooklist.View.RegisterScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment {

    private Button cikis_btn;
    private EditText editName,editEmail;
    private View v;
    private FirebaseFirestore mFirestore;
    private DocumentReference mRef;
    private FirebaseUser mUser;
    private Kullanici user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_user, container, false);

        editName = v.findViewById(R.id.user_editName);
        editEmail = v.findViewById(R.id.user_editEmail);
        cikis_btn = v.findViewById(R.id.cikis_btn);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        mRef = mFirestore.collection("Kullanıcılar").document(mUser.getUid());
        mRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error!=null){
                    Toast.makeText(v.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(value != null && value.exists()){
                    user = value.toObject(Kullanici.class);
                    if(user != null){
                        editName.setText(user.getKullaniciAdi());
                        editEmail.setText(user.getKullaniciEmail());


                    }
                }
            }
        });
        cikis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x =1;
                Intent intent = new Intent(getContext(), LoginScreen.class);
                Bundle bundle = new Bundle();
                bundle.putInt("keyInt",x);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        return v;

    }

}