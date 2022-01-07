package com.example.mybooklist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybooklist.Model.Kitap;
import com.example.mybooklist.R;
import com.example.mybooklist.View.BookDetails;
import com.example.mybooklist.View.LoginScreen;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;

public class KitapAdapter extends RecyclerView.Adapter<KitapAdapter.KitapHolder> {
    private ArrayList<Kitap> mKitapList;
    private Context mContext;
    private View v;
    private Kitap mKitap;
    int kPos;
    private ImageView delete;
    String kitap_adi_detail;

    public KitapAdapter(ArrayList<Kitap> mKitapList, Context mContext) {
        this.mKitapList = mKitapList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public KitapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(mContext).inflate(R.layout.kitap_ogesi,parent,false);
        return new KitapHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KitapHolder holder, int position) {
        //gelen verileri aktardığımız kodlar.
        mKitap = mKitapList.get(position);


        holder.kitap_adi.setText(mKitap.getKitapAdi());

        Picasso.get().load(mKitap.getKitapResmi()).resize(66,66).into(holder.kitap_resmi);


        holder.itemView.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {


                kPos = holder.getAdapterPosition();
                if(kPos != RecyclerView.NO_POSITION){


                        Intent intent = new Intent(mContext,BookDetails.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key_kitap_resmi",mKitapList.get(kPos).getKitapResmi());
                        bundle.putString("key_kitap_adi",mKitapList.get(kPos).getKitapAdi());
                        bundle.putString("key_yazar_adi",mKitapList.get(kPos).getKitapYazari());
                        bundle.putString("key_kitap_turu",mKitapList.get(kPos).getKitapTuru());
                        bundle.putString("key_sayfa_sayisi",mKitapList.get(kPos).getSayfaSayisi());
                        bundle.putString("key_kitap_konusu",mKitapList.get(kPos).getKitapKonusu());
                        bundle.putString("key_kullanici_id",mKitapList.get(kPos).getEkleyen());

                        intent.putExtras(bundle);
                        mContext.startActivity(intent);




                  //  mContext.startActivity(new Intent(mContext, BookDetails.class));


                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mKitapList.size();
    }

    class KitapHolder extends RecyclerView.ViewHolder{
        TextView kitap_adi;
        CircleImageView kitap_resmi;
        ImageView delete;

        public KitapHolder(@NonNull View itemView) {
            super(itemView);
            kitap_adi = itemView.findViewById(R.id.kitap_adi);
            kitap_resmi = itemView.findViewById(R.id.kitap_resmi);

        }
    }




}
