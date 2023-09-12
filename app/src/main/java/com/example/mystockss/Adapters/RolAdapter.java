package com.example.mystockss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystockss.Class.Rol;
import com.example.mystockss.R;

import java.util.ArrayList;

public class RolAdapter extends RecyclerView.Adapter <RolAdapter.RolHolder>{
    public ArrayList<Rol> rolArrayList;
    private Context mContext;

    public RolAdapter(ArrayList<Rol> rolArrayList, Context mContext) {
        this.rolArrayList = rolArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rol_item,parent,false);
        return new RolHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RolHolder holder, int position) {
        holder.rol.setText(rolArrayList.get(position).getRol());
    }

    @Override
    public int getItemCount() {
        return rolArrayList.size();
    }

    class RolHolder extends RecyclerView.ViewHolder{

        TextView rol;
        public RolHolder(@NonNull View itemView) {
            super(itemView);
            rol = itemView.findViewById(R.id.rolTextView);
        }
    }
}
