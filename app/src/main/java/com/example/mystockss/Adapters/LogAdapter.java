package com.example.mystockss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystockss.Class.Loglar;
import com.example.mystockss.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter <LogAdapter.LogHolder> {
    public ArrayList<Loglar> loglarList;
    private Context mContext;

    public LogAdapter(ArrayList<Loglar> loglarList, Context mContext) {
        this.loglarList = loglarList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.log_item,parent,false);
        return new LogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogHolder holder, int position) {
        Picasso.get().load(loglarList.get(position).getResimUri()).into(holder.teslimAlanImza);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = formatter.format((loglarList.get(position).getTimestamp()));
        holder.envanterAdi.setText(loglarList.get(position).getEnvanterAdi());
        if (loglarList.get(position).getIslem().equals("cikarma")){
            holder.islemTuru.setText("İşlem Türü: Çıkarma");
            holder.islemYapan.setText(loglarList.get(position).getIslemSahibi());
            holder.islemTarihi.setText(formattedDate);
            holder.teslimAlan.setText(loglarList.get(position).getEnvanteriTeslimAlan());
            holder.stokAdet.setText("Çıkarılan Stok: "+loglarList.get(position).getStokAdet());

        }
        else{
            holder.islemTuru.setText("İşlem Türü: Ekleme");
            holder.islemYapan.setText(loglarList.get(position).getIslemSahibi());
            holder.islemTarihi.setText(formattedDate);
            holder.linearLayout.setVisibility(View.GONE);
            holder.stokAdet.setText("Eklenen Stok: "+loglarList.get(position).getStokAdet());
        }

    }

    @Override
    public int getItemCount() {
        return loglarList.size();
    }

    class LogHolder extends RecyclerView.ViewHolder{
        TextView islemTuru,islemYapan,islemTarihi,teslimAlan,stokAdet,envanterAdi;
        ImageView teslimAlanImza;
        LinearLayout linearLayout;

        public LogHolder(@NonNull View itemView) {
            super(itemView);
            islemTuru = itemView.findViewById(R.id.islemTuru_LogActivity);
            islemYapan = itemView.findViewById(R.id.islemYapan_LogActivity);
            islemTarihi = itemView.findViewById(R.id.islemTarihi_LogActivity);
            teslimAlan = itemView.findViewById(R.id.teslimAlan_LogActivity);
            stokAdet = itemView.findViewById(R.id.logItem_stokAdet);
            envanterAdi = itemView.findViewById(R.id.envanterAdi_LogActivity);
            teslimAlanImza = itemView.findViewById(R.id.imageView_LogActivity);
            linearLayout = itemView.findViewById(R.id.teslimAlanLinearLayout);

        }
    }
}
