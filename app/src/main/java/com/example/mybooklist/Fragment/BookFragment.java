package com.example.mybooklist.Fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybooklist.Adapter.KitapAdapter;
import com.example.mybooklist.Model.Kitap;
import com.example.mybooklist.Model.Kullanici;
import com.example.mybooklist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class BookFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private View v;
    private KitapAdapter mAdapter;
    private FirebaseUser mUser;
    private ArrayList<Kitap> mKitapList;
    private Kitap mKitap;
    private Query mQuery;
    private FirebaseFirestore mFireStore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_book, container, false);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFireStore = FirebaseFirestore.getInstance();
        mKitapList = new ArrayList<>();
        mRecyclerView = v.findViewById(R.id.book_fragment_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(),LinearLayoutManager.VERTICAL,false));
        mQuery = mFireStore.collection("Kitaplar");
        mQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(v.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(value != null){
                    mKitapList.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        mKitap = snapshot.toObject(Kitap.class);
                        assert  mKitap != null; // kullanıcı objesinin boş olmadığını kontrol ediyoruz
                        if(mKitap.getEkleyen().equals(mUser.getUid())){ // sadece giriş yapan kullanıcıya ait kitapları listelemek istediğimiz için bu kontrolü yapıyoruz
                            mKitapList.add(mKitap);

                        }
                        mRecyclerView.addItemDecoration(new LinearDecoration(10,mKitapList.size()));
                        mAdapter = new KitapAdapter(mKitapList, v.getContext());
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }

            }
        });




        return v;
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