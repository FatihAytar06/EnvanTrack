package com.example.mystockss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystockss.Activities.StokActivity;
import com.example.mystockss.Activities.TalepDetailsActivity;
import com.example.mystockss.R;
import com.example.mystockss.Class.Talepler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TalepAdapter extends RecyclerView.Adapter<TalepAdapter.TalepHolder> {
    public ArrayList<Talepler> talepList;
    private Context mContext;

    public TalepAdapter(ArrayList<Talepler> talepList, Context mContext) {
        this.talepList = talepList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TalepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.talep_item,parent,false);
        return new TalepHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TalepHolder holder, int position) {

        holder.personelAdi.setText(talepList.get(position).getTalepEden());
        holder.departman.setText(talepList.get(position).getTalepBirim());
        holder.talepAdi.setText(talepList.get(position).getItemAdi());
        holder.talepAdet.setText(talepList.get(position).getTalepAdet());
        Picasso.get().load(talepList.get(position).getTalepResim()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), TalepDetailsActivity.class);
                intent.putExtra("resUri",talepList.get(position).getTalepResim());
                intent.putExtra("talepAdet",talepList.get(position).getTalepAdet());
                intent.putExtra("stokAdi",talepList.get(position).getItemAdi());
                intent.putExtra("talenEden",talepList.get(position).getTalepEden());
                intent.putExtra("docId",talepList.get(position).getDocId());
                intent.putExtra("talepTarihi",talepList.get(position).getTalepTarihi());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return talepList.size();
    }


    class TalepHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView personelAdi,departman,talepAdi,talepAdet;
        public TalepHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.talepResimImageView);
            personelAdi = itemView.findViewById(R.id.personelAdi_talepActivity);
            departman = itemView.findViewById(R.id.birim_TalepActivity);
            talepAdi = itemView.findViewById(R.id.talepEdilen_TextView);
            talepAdet = itemView.findViewById(R.id.talepAdet_TextView);
        }
    }
}
