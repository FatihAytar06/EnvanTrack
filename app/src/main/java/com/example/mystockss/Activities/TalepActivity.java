package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mystockss.Adapters.TalepAdapter;
import com.example.mystockss.Class.Talepler;
import com.example.mystockss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class TalepActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView mRecyclerView;
    private TalepAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Talepler> talepList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep);

        mRecyclerView = findViewById(R.id.talep_activity_recyclerview);
        adapter=new TalepAdapter(Talepler.getData(),this);

        talepList = new ArrayList<>();
        talepList.clear();
        searchView = findViewById(R.id.searchView_ActivityTalep);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int permissionLevel = sharedPreferences.getInt("permissionLevel", 0);

        if (permissionLevel == 1) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            if (currentUser != null) {
                String email = currentUser.getEmail();
                getData(email);
            }
        }
        else{
            getData();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterData(newText);

                    return true;
                }
            });


        }




    }

    private void getData(){

        db.collection("talepler").orderBy("talepTarihi")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.talepList.clear();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {

                            Talepler talep = documentSnapshot.toObject(Talepler.class);
                            // Eşya verilerini kullanabilirsiniz
                            String talepEdenAdi = talep.getTalepEden();
                            String talepEdenEmail = talep.getTalepEdenEmail();
                            String itemAdi = talep.getItemAdi();
                            boolean aktiflik = talep.isAktiflik();
                            String talepAdet = talep.getTalepAdet();
                            String talepBirim = talep.getTalepBirim();
                            String talepResim = talep.getTalepResim();
                            String docId = talep.getDocId();
                            Date talepTarihi = talep.getTalepTarihi();
                            if (aktiflik){
                                Talepler talepler = new Talepler(talepEdenAdi,talepEdenEmail,itemAdi,talepAdet,talepBirim,talepResim,talepTarihi,docId,aktiflik);
                                talepList.add(talepler);
                            }



                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(TalepActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    private void getData(String email){

        db.collection("talepler").orderBy("talepTarihi")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.talepList.clear();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {

                            Talepler talep = documentSnapshot.toObject(Talepler.class);
                            // Eşya verilerini kullanabilirsiniz
                            String talepEdenAdi = talep.getTalepEden();
                            String talepEdenEmail = talep.getTalepEdenEmail();
                            String itemAdi = talep.getItemAdi();
                            boolean aktiflik = talep.isAktiflik();
                            String talepAdet = talep.getTalepAdet();
                            String talepBirim = talep.getTalepBirim();
                            String talepResim = talep.getTalepResim();
                            Date talepTarihi = talep.getTalepTarihi();
                            String docId = talep.getDocId();
                            if (talepEdenEmail.equals(email) && aktiflik==true){
                                Talepler talepler = new Talepler(talepEdenAdi,talepEdenEmail,itemAdi,talepAdet,talepBirim,talepResim,talepTarihi,docId,aktiflik);
                                talepList.add(talepler);
                            }




                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(TalepActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    private void filterData(String text){

        db.collection("talepler").orderBy("talepTarihi")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.talepList.clear();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {

                            Talepler talep = documentSnapshot.toObject(Talepler.class);
                            // Eşya verilerini kullanabilirsiniz
                            String talepEdenAdi = talep.getTalepEden();
                            String talepEdenEmail = talep.getTalepEdenEmail();
                            String itemAdi = talep.getItemAdi();
                            boolean aktiflik = talep.isAktiflik();
                            String talepAdet = talep.getTalepAdet();
                            String talepBirim = talep.getTalepBirim();
                            String talepResim = talep.getTalepResim();
                            Date talepTarihi = talep.getTalepTarihi();
                            String docId = talep.getDocId();
                            if (talepEdenAdi.toLowerCase().contains(text.toLowerCase()) && aktiflik==true){
                                Talepler talepler = new Talepler(talepEdenAdi,talepEdenEmail,itemAdi,talepAdet,talepBirim,talepResim,talepTarihi,docId,aktiflik);
                                talepList.add(talepler);
                            }




                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(TalepActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TalepActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}