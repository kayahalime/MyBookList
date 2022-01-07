package com.example.mybooklist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybooklist.Model.Alinti;
import com.example.mybooklist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AlintiAdapter extends RecyclerView.Adapter<AlintiAdapter.ViewHolder>{

    private Context mContext;
    private List<Alinti> mAlintiListesi;
    private FirebaseUser mevcutKullanici;
    private Alinti mAlinti;

    public AlintiAdapter(List<Alinti> mAlintiListesi, Context mContext) {
        this.mAlintiListesi = mAlintiListesi;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alinti_ogesi,viewGroup,false);
        return new AlintiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();

        mAlinti = mAlintiListesi.get(i);

        holder.txt_alinti.setText(mAlinti.getAlinti());
        //i ile listedeki mevcut satırı alıyor.


    }

    @Override
    public int getItemCount() {
        return mAlintiListesi.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_alinti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_alinti = itemView.findViewById(R.id.alintilanan_metin);
        }
    }

}
