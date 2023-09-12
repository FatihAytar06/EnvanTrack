package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystockss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalepolusturActivity extends AppCompatActivity {
    TextView stokAdet,talepEden;
    EditText departman;
    Spinner cikarilacakStokSayisi;
    Button confirmBtn;
    ImageView imageView;
    int secilenDeger = 1;
    String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talepolustur);
        stokAdet = findViewById(R.id.stokAdedi_TalepActivity_TextView);
        talepEden = findViewById(R.id.talepEdenAdTextView);
        departman = findViewById(R.id.talepEdenDepartmanTextView);
        cikarilacakStokSayisi = findViewById(R.id.talepSpinner);
        confirmBtn = findViewById(R.id.talepOnaylaBtn);
        imageView = findViewById(R.id.detailImageViewActivityTalep);


        String envanterAdi = getIntent().getStringExtra("envanterAdi");
        String marka = getIntent().getStringExtra("marka");
        String stokadedi = getIntent().getStringExtra("stokAdet");
        String resimUri = getIntent().getStringExtra("resUri");
        docId = getIntent().getStringExtra("docId");
        if (resimUri != null&&envanterAdi !=null && stokadedi !=null) {
            // Resmi indirin ve ImageView'a yükleyin
            Picasso.get().load(resimUri).into(imageView);
            stokAdet.setText("Stok adedi : "+stokadedi);


            List<Integer> numbersList = new ArrayList<>();
            for (int i = 1; i <= Integer.valueOf(stokadedi); i++) {
                numbersList.add(i);
            }

            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbersList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            cikarilacakStokSayisi.setAdapter(adapter);


        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            if (email!=null){
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                CollectionReference usersCollection = firebaseFirestore.collection("kullanicilar");

                Query query = usersCollection.whereEqualTo("email", email);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            // İlgili dökümanı bulduk, burada istediğiniz verileri alabilirsiniz
                            String ad = documentSnapshot.getString("isim");
                            talepEden.setText(ad);


                        } else {
                            // İlgili döküman bulunamadı, gerekli işlemleri yapabilirsiniz
                        }
                    } else {
                        // Sorgu başarısız oldu, gerekli işlemleri yapabilirsiniz
                    }
                });
            }

        }

        cikarilacakStokSayisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilenDeger = Integer.valueOf(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                secilenDeger = 1;
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                talepOlustur(resimUri,envanterAdi,marka,secilenDeger);
            }
        });
    }

    private void talepOlustur(String resimUri, String envanterAdi,String itemMarka,int talepMiktari) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String email="";
        if (currentUser != null) {
            email = currentUser.getEmail();
        }
        Map<String, Object> itemData = new HashMap<>();
        itemData.put("itemAdi", envanterAdi);
        itemData.put("talepEden",talepEden.getText());
        itemData.put("talepEdenEmail",email);
        itemData.put("aktiflik",true);
        itemData.put("docId",docId);
        itemData.put("talepBirim",departman.getText().toString());
        itemData.put("talepAdet", String.valueOf(talepMiktari));
        itemData.put("talepResim", resimUri);
        itemData.put("talepTarihi", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance().collection("talepler")
                .add(itemData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(TalepolusturActivity.this, "Talep başarıyla oluşturuldu!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TalepolusturActivity.this, TalepActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(TalepolusturActivity.this, "Veri ekleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}