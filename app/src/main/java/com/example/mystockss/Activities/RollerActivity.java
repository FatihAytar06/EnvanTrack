package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mystockss.Adapters.RolAdapter;
import com.example.mystockss.Adapters.StokAdapter;
import com.example.mystockss.Class.Rol;
import com.example.mystockss.Class.Stoklar;
import com.example.mystockss.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class RollerActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    private RolAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Rol> rollerList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roller);

        mRecyclerView = findViewById(R.id.roller_activity_recyclerview);
        adapter = new RolAdapter(Rol.getData(),this);

        rollerList = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        getData();

    }
    private void getData(){
        db.collection("roller")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.rolArrayList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            Rol rol = documentSnapshot.toObject(Rol.class);
                            // Eşya verilerini kullanabilirsiniz
                            String userRol = rol.getRol();
                            Rol roller = new Rol(userRol);
                            rollerList.add(roller);


                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(RollerActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }


}