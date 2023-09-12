package com.example.mystockss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystockss.R;
import com.example.mystockss.Class.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    public ArrayList<User> userList;
    private Context mContext;


    public UserAdapter(ArrayList<User> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        if (userList.get(position).getYetkiSeviyesi().equals("1")){
            holder.yetkiSeviyesi.setText("Kullanıcı");
        }
        else if (userList.get(position).getYetkiSeviyesi().equals("2")){
            holder.yetkiSeviyesi.setText("Görevli");
        }
        else{
            holder.yetkiSeviyesi.setText("Admin");
        }
        holder.birim.setText(userList.get(position).getBirim());
        holder.email.setText(userList.get(position).getEmail());
        holder.personelAdi.setText(userList.get(position).getIsim());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView personelAdi,birim,email,yetkiSeviyesi;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            personelAdi = itemView.findViewById(R.id.personelAdi_userActivity);
            birim = itemView.findViewById(R.id.birim_userActivity);
            email = itemView.findViewById(R.id.email_userActivity);
            yetkiSeviyesi = itemView.findViewById(R.id.yetkiSeviyesi_userActivity);
        }
    }
}
