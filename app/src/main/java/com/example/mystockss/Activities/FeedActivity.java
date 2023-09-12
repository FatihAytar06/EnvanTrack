package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystockss.Adapters.StokAdapter;
import com.example.mystockss.Class.Stoklar;
import com.example.mystockss.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class FeedActivity extends AppCompatActivity {
    SearchView searchView;
    ImageView addItem;
    RecyclerView mRecyclerView;
    private StokAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Stoklar> stokList ;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mRecyclerView = findViewById(R.id.feed_activity_recyclerview);
        adapter=new StokAdapter(Stoklar.getData(),this);
        addItem = findViewById(R.id.addItemImageView);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int permissionLevel = sharedPreferences.getInt("permissionLevel", 0);

        if (permissionLevel == 1) {
            addItem.setVisibility(View.GONE);
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedActivity.this,AddItemActivity.class);
                startActivity(intent);
            }
        });

        stokList = new ArrayList<>();
        stokList.clear();
        searchView = findViewById(R.id.searchView_ActivityFeed);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        getData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                return true;
            }
        });
    }

    private void getData(String itemAdi){
        db.collection("item").orderBy("eklenmeTarihi")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.stoklarList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            Stoklar stok = documentSnapshot.toObject(Stoklar.class);
                            // Eşya verilerini kullanabilirsiniz
                            String ad = stok.getItemAdi();
                            String adet = stok.getItemAdet();
                            String marka = stok.getMarka();
                            String resimUrl = stok.getItemResim();
                            String docId = documentSnapshot.getId();
                            String birimFiyat = stok.getBirimFiyat();
                            Date eklenmeTarihi = stok.getEklenmeTarihi();
                            if (ad.toLowerCase().contains(itemAdi.toLowerCase())){
                                Stoklar stocks = new Stoklar(ad,marka,adet,resimUrl,birimFiyat,eklenmeTarihi,docId);
                                stokList.add(stocks);
                            }



                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(FeedActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    private void getData(){
        db.collection("item").orderBy("eklenmeTarihi")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.stoklarList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            Stoklar stok = documentSnapshot.toObject(Stoklar.class);
                            // Eşya verilerini kullanabilirsiniz
                            String ad = stok.getItemAdi();
                            String marka = stok.getMarka();
                            String adet = stok.getItemAdet();
                            String resimUrl = stok.getItemResim();
                            String docId = documentSnapshot.getId();
                            String birimFiyat = stok.getBirimFiyat();
                            Date eklenmeTarihi = stok.getEklenmeTarihi();
                            Stoklar stocks = new Stoklar(ad,marka,adet,resimUrl,birimFiyat,eklenmeTarihi,docId);
                            stokList.add(stocks);


                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(FeedActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FeedActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}