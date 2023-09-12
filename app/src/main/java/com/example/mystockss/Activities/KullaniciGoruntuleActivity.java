package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mystockss.Adapters.UserAdapter;
import com.example.mystockss.Class.User;
import com.example.mystockss.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class KullaniciGoruntuleActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private UserAdapter userAdapter;
    ArrayList<User> userList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView addUser;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_goruntule);
        userList = new ArrayList<>();
        userList.clear();
        searchView = findViewById(R.id.searchView_ActivityKullaniciGoruntule);
        userAdapter = new UserAdapter(User.getData(),this);
        mRecyclerView = findViewById(R.id.kullaniciGoruntule_activity_recyclerview);
        addUser = findViewById(R.id.addUserImageView);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(userAdapter);

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
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KullaniciGoruntuleActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData(){
        db.collection("kullanicilar").orderBy("isim")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userAdapter.userList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            // Eşya verilerini kullanabilirsiniz
                            String isim = user.getIsim();
                            String birim = user.getBirim();
                            String email = user.getEmail();
                            String yetkiSeviyesi = user.getYetkiSeviyesi();

                            User usr = new User(isim,birim,email,yetkiSeviyesi);
                            userList.add(usr);


                        }
                    }
                    userAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(KullaniciGoruntuleActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    private void getData(String text){
        db.collection("kullanicilar").orderBy("isim")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userAdapter.userList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            // Eşya verilerini kullanabilirsiniz
                            String isim = user.getIsim();
                            String birim = user.getBirim();
                            String email = user.getEmail();
                            String yetkiSeviyesi = user.getYetkiSeviyesi();
                            if(isim.toLowerCase().contains(text.toLowerCase())){
                                User usr = new User(isim,birim,email,yetkiSeviyesi);
                                userList.add(usr);
                            }



                        }
                    }
                    userAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Hata durumu
                    Toast.makeText(KullaniciGoruntuleActivity.this, "Veri çekme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(KullaniciGoruntuleActivity.this,MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}