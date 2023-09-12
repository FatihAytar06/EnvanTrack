package com.example.mystockss.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mystockss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {
    Button envanterBtn,yardimBtn,logKayitBtn,kullaniciGoruntuleBtn,taleplerimBtn,rollerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        envanterBtn = findViewById(R.id.envanterBtn);
        yardimBtn = findViewById(R.id.yardimBtn);
        logKayitBtn = findViewById(R.id.logKayitlariBtn);
        kullaniciGoruntuleBtn = findViewById(R.id.kullaniciGoruntuleBtn);
        taleplerimBtn = findViewById(R.id.taleplerimBtn);
        rollerBtn = findViewById(R.id.rollerBtn);

        envanterBtn.setVisibility(View.GONE);
        yardimBtn.setVisibility(View.GONE);
        logKayitBtn.setVisibility(View.GONE);
        kullaniciGoruntuleBtn.setVisibility(View.GONE);
        taleplerimBtn.setVisibility(View.GONE);
        rollerBtn.setVisibility(View.GONE);


        envanterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        rollerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,RollerActivity.class);
                startActivity(intent);
            }
        });

        kullaniciGoruntuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,KullaniciGoruntuleActivity.class);
                startActivity(intent);
            }
        });


        logKayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,CalenderActivity.class);
                startActivity(intent);
            }
        });
        taleplerimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,TalepActivity.class);
                startActivity(intent);
            }
        });

        yardimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,CallActivity.class);
                startActivity(intent);
            }
        });



    }

    private void checkPermission() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUserId = auth.getCurrentUser().getEmail();

        String USERS_COLLECTION = "kullanicilar";
        String EMAIL_FIELD = "email";
        String PERMISSION_FIELD = "yetkiSeviyesi";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS_COLLECTION)
                .whereEqualTo(EMAIL_FIELD, currentUserId)
                .limit(1) // Bu satır, sadece bir sonuç döndürmesini sağlar, çünkü mail adresi benzersiz olmalıdır.
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        if (!task.getResult().isEmpty()) {
                            // Firestore'dan kullanıcıya ait veriyi al
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String permissionLevel = document.getString(PERMISSION_FIELD);

                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("permissionLevel", Integer.valueOf(permissionLevel));
                            editor.apply();

                            if (permissionLevel.equals("1")){
                                taleplerimBtn.setVisibility(View.VISIBLE);
                                yardimBtn.setVisibility(View.VISIBLE);
                                envanterBtn.setVisibility(View.VISIBLE);
                            }
                            else if(permissionLevel.equals("2")){
                                taleplerimBtn.setVisibility(View.VISIBLE);
                                envanterBtn.setVisibility(View.VISIBLE);
                                yardimBtn.setVisibility(View.VISIBLE);
                                taleplerimBtn.setText("TALEPLER");

                            }
                            else if(permissionLevel.equals("3")){
                                taleplerimBtn.setVisibility(View.VISIBLE);
                                envanterBtn.setVisibility(View.VISIBLE);
                                kullaniciGoruntuleBtn.setVisibility(View.VISIBLE);
                                logKayitBtn.setVisibility(View.VISIBLE);
                                rollerBtn.setVisibility(View.VISIBLE);
                                taleplerimBtn.setText("TALEPLER");

                            }


                        } else {
                            // Firestore'da kullanıcıya ait veri yok
                        }
                    } else {
                        // Firestore'a erişim hatası
                    }
                });

    }
}