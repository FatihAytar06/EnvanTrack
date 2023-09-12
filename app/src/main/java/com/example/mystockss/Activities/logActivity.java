package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mystockss.Adapters.LogAdapter;
import com.example.mystockss.Class.Loglar;
import com.example.mystockss.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class logActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private LogAdapter adapter;

    ArrayList<Loglar> logList ;
    Date startDate,endDate;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mRecyclerView = findViewById(R.id.log_activity_recyclerview);
        adapter = new LogAdapter(Loglar.getData(),this);
        logList = new ArrayList<>();
        logList.clear();
        searchView = findViewById(R.id.searchView_ActivityLog);


        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

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
        getData();
    }
    private void getData(){

        long startLong = getIntent().getLongExtra("startDate", 0);
        long endLong = getIntent().getLongExtra("endDate", 0);

        startDate = new Date(startLong);
        endDate = new Date(endLong);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference logsRef = db.collection("loglar");

        logsRef.whereGreaterThanOrEqualTo("timestamp", startDate)
                .whereLessThanOrEqualTo("timestamp", endDate)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.loglarList.clear();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                        Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");
                        Date date = timestamp.toDate();

                        if (documentSnapshot.exists()) {
                            Loglar log = documentSnapshot.toObject(Loglar.class);
                            // Eşya verilerini kullanabilirsiniz
                            String ad = log.getEnvanterAdi();
                            String adet = String.valueOf(log.getStokAdet());
                            String islemSahibi = log.getIslemSahibi();
                            String resimUri = log.getResimUri();
                            String islem = log.getIslem();
                            String teslimAlan = log.getEnvanteriTeslimAlan();
                            Date tarih = log.getTimestamp();
                            String docId = documentSnapshot.getId();

                            Loglar logs = new Loglar(ad,teslimAlan,islem,islemSahibi,resimUri,adet,tarih);
                            logList.add(logs);


                        }

                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumunda işlemleri gerçekleştirin
                });



    }
    private void getData(String text){

        long startLong = getIntent().getLongExtra("startDate", 0);
        long endLong = getIntent().getLongExtra("endDate", 0);

        startDate = new Date(startLong);
        endDate = new Date(endLong);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference logsRef = db.collection("loglar");

        logsRef.whereGreaterThanOrEqualTo("timestamp", startDate)
                .whereLessThanOrEqualTo("timestamp", endDate)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    adapter.loglarList.clear();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                        Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");
                        Date date = timestamp.toDate();

                        if (documentSnapshot.exists()) {
                            Loglar log = documentSnapshot.toObject(Loglar.class);
                            // Eşya verilerini kullanabilirsiniz
                            String ad = log.getEnvanterAdi();
                            String adet = String.valueOf(log.getStokAdet());
                            String islemSahibi = log.getIslemSahibi();
                            String resimUri = log.getResimUri();
                            String islem = log.getIslem();
                            String teslimAlan = log.getEnvanteriTeslimAlan();
                            Date tarih = log.getTimestamp();
                            String docId = documentSnapshot.getId();

                            if (ad.toLowerCase().contains(text.toLowerCase())){
                                Loglar logs = new Loglar(ad,teslimAlan,islem,islemSahibi,resimUri,adet,tarih);
                                logList.add(logs);
                            }




                        }

                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumunda işlemleri gerçekleştirin
                });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(logActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}