package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystockss.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kyanogen.signatureview.SignatureView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class stokEkleActivity extends AppCompatActivity {

    ImageView imageView;
    TextView stokEkleyenKisi,stokAdedi;
    SignatureView stokEkleyenImza;
    Spinner eklenecekStokSayisi;
    Button clearBtn,confirmBtn;
    String docId,envanterAdi;
    int secilenDeger = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stok_ekle);

        imageView = findViewById(R.id.stokEkleImageView);
        stokEkleyenKisi = findViewById(R.id.stoktEkleyenTextView);
        stokEkleyenImza = findViewById(R.id.stokEkle_signature_view);
        eklenecekStokSayisi = findViewById(R.id.stokEkleSpinner);
        stokAdedi = findViewById(R.id.stokAdedi_stokEkle_TextView);


        clearBtn = findViewById(R.id.clearSignatureBtn_stokEkle);
        confirmBtn = findViewById(R.id.confirmBtn_stokEkle);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stokEkleyenImza.clearCanvas();
            }
        });

        eklenecekStokSayisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilenDeger = Integer.valueOf(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                secilenDeger = 1;
            }
        });

        docId = getIntent().getStringExtra("docId");
        envanterAdi = getIntent().getStringExtra("envanterAdi");

        String resimUri = getIntent().getStringExtra("resUri");
        int stokAdet = Integer.valueOf(getIntent().getStringExtra("stokAdet"));
        if (resimUri != null&&stokAdet!=0) {
            // Resmi indirin ve ImageView'a yükleyin
            Picasso.get().load(resimUri).into(imageView);
            stokAdedi.setText("Stok adedi : "+stokAdet);

            List<Integer> numbersList = new ArrayList<>();
            for (int i = 1; i <= 500; i++) {
                numbersList.add(i);
            }

            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbersList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            eklenecekStokSayisi.setAdapter(adapter);

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
                            stokEkleyenKisi.setText(ad);


                        } else {
                            // İlgili döküman bulunamadı, gerekli işlemleri yapabilirsiniz
                        }
                    } else {
                        // Sorgu başarısız oldu, gerekli işlemleri yapabilirsiniz
                    }
                });
            }

    }
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBtn.setEnabled(false);
                Bitmap bitmap = stokEkleyenImza.getSignatureBitmap();

                if (bitmap != null){

                    String ekleyenKisi = stokEkleyenKisi.getText().toString();


                    //
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

                    // Bitmap'i resme dönüştür
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();

                    // Firestorage'a resmi yükleyin
                    StorageReference imageRef = storageRef.child("images/" + UUID.randomUUID().toString() + ".jpg");
                    UploadTask uploadTask = imageRef.putBytes(imageData);
                    uploadTask.continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Yüklenen resmin indirme URL'sini alın
                        return imageRef.getDownloadUrl();
                    }).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Resim yükleme işlemi ve indirme URL'si alma işlemi başarılı

                            // Resmin indirme URL'sini alın
                            Uri downloadUri = task.getResult();
                            stokEklemeLogKaydiOlustur(ekleyenKisi,envanterAdi,secilenDeger,downloadUri,docId);
                        } else {
                            // Resim yükleme veya indirme hatası durumunda buraya gelecek kodları ekleyin
                        }
                    });
                }
            }
        });
}

    public void stokEklemeLogKaydiOlustur(String stokEkleyenKisi, String envanterAdi,int eklenenAdet, Uri resimUri, String envanterId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Map<String, Object> logData = new HashMap<>();
        logData.put("islemSahibi", stokEkleyenKisi);
        logData.put("envanterAdi", envanterAdi);
        logData.put("stokAdet", String.valueOf(eklenenAdet));
        logData.put("envanteriTeslimAlan", stokEkleyenKisi);
        logData.put("resimUri", resimUri);
        logData.put("islem", "ekleme");
        logData.put("timestamp", FieldValue.serverTimestamp());

        db.collection("loglar")
                .add(logData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        DocumentReference envanterRef = db.collection("item").document(envanterId);

                        envanterRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    int mevcutStokSayisi = Integer.valueOf(documentSnapshot.getString("itemAdet"));
                                    // Mevcut stok sayısını kullanabilirsiniz
                                    if (mevcutStokSayisi != 0) {
                                        int yeniStokSayisi = mevcutStokSayisi + eklenenAdet;
                                        envanterRef.update("itemAdet", String.valueOf(yeniStokSayisi))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(stokEkleActivity.this,MenuActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            Toast.makeText(stokEkleActivity.this,"Başarılı!",Toast.LENGTH_SHORT);
                                            finish();
                                            startActivity(intent);
                                        }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Stok sayısı güncelleme hatası
                                        }
                                        });


                                    }
                                } else {

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Firestore'dan stok sayısı alınamadı
                            }
                        });







                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log kaydı ekleme hatası
                    }
                });


    }

}