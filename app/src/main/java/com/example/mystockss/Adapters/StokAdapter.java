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

import com.example.mystockss.R;
import com.example.mystockss.Activities.StokActivity;
import com.example.mystockss.Class.Stoklar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StokAdapter extends RecyclerView.Adapter <StokAdapter.StokHolder>{
    public ArrayList<Stoklar> stoklarList;
    private Context context;

    public StokAdapter(ArrayList<Stoklar> stoklarList, Context context) {
        this.stoklarList = stoklarList;
        this.context = context;
    }

    @NonNull
    @Override
    public StokHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new StokHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StokHolder holder, int position) {
        holder.itemAdi.setText(stoklarList.get(position).getItemAdi());
        holder.itemAdet.setText("Stok adedi :" + stoklarList.get(position).getItemAdet()+" adet");
        Picasso.get().load(stoklarList.get(position).getItemResim()).into(holder.itemResim);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), StokActivity.class);
                intent.putExtra("resUri",stoklarList.get(position).getItemResim());
                intent.putExtra("stokAdet",stoklarList.get(position).getItemAdet());
                intent.putExtra("stokAdi",stoklarList.get(position).getItemAdi());
                intent.putExtra("docId",stoklarList.get(position).getDocId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stoklarList.size();
    }

    class StokHolder extends RecyclerView.ViewHolder{

        TextView itemAdi,itemAdet;
        ImageView itemResim;
        public StokHolder(@NonNull View itemView) {
            super(itemView);
            itemAdi = itemView.findViewById(R.id.list_item_textView_itemadi);
            itemAdet = itemView.findViewById(R.id.list_item_textView_itemadet);
            itemResim = itemView.findViewById(R.id.list_item_imageView);

        }

    }
}
