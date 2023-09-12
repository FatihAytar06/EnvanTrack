package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mystockss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText isim,birim,email,parola;
    Button registerBtn;
    Spinner spinner;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    int secilenDeger = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        isim = findViewById(R.id.nameTextView);
        birim = findViewById(R.id.birimTextView);
        email = findViewById(R.id.kayitEmailTextView);
        parola = findViewById(R.id.kayitParolaTextView);
        registerBtn = findViewById(R.id.kaydolBtn);
        spinner = findViewById(R.id.yetkiDuzeyiSpinner);

        List<String> yetkiList = new ArrayList<>();
        yetkiList.add("Kullanıcı");
        yetkiList.add("Görevli");
        yetkiList.add("Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yetkiList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilenDeger = Integer.valueOf(adapterView.getSelectedItemPosition()+1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                secilenDeger = 1;
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eposta = email.getText().toString();
                String sifre = parola.getText().toString();
                String ad = isim.getText().toString();
                String birm = birim.getText().toString();
                String yetkiSeviyesi = String.valueOf(secilenDeger);





                if (eposta.isEmpty()||sifre.isEmpty()||ad.isEmpty()||birm.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Lütfen boş alan bırakmayınız.",Toast.LENGTH_SHORT).show();

                }
                else{
                    if (sifre.length()<6){
                        Toast.makeText(RegisterActivity.this,"Şifre 6 haneden kısa olamaz!",Toast.LENGTH_SHORT).show();
                    }
                auth.fetchSignInMethodsForEmail(eposta)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> signInMethods = task.getResult().getSignInMethods();
                                if (signInMethods != null && !signInMethods.isEmpty()) {
                                    // Kullanıcı zaten kayıtlı
                                    Toast.makeText(RegisterActivity.this,"Kullanıcı zaten kayıtlı!",Toast.LENGTH_SHORT).show();
                                } else {
                                    // Kullanıcı kayıtlı değil, üye olma işlemini gerçekleştir
                                    auth.createUserWithEmailAndPassword(eposta, sifre)
                                            .addOnCompleteListener(registerTask -> {
                                                if (registerTask.isSuccessful()) {
                                                    // Yeni kullanıcı oluşturma başarılı
                                                    Toast.makeText(RegisterActivity.this,"Kayıt Başarılı!",Toast.LENGTH_SHORT).show();

                                                    Map<String, String> usr = new HashMap<>();
                                                    usr.put("email",eposta);
                                                    usr.put("isim",ad);
                                                    usr.put("birim",birm);
                                                    usr.put("yetkiSeviyesi",yetkiSeviyesi);

                                                    db.collection("kullanicilar")
                                                            .add(usr)
                                                            .addOnSuccessListener(documentReference -> {
                                                                // Veri başarıyla eklendi
                                                                Log.d("TAG", "Veri eklendi. Belge ID: " + documentReference.getId());
                                                                Intent intent = new Intent(RegisterActivity.this,KullaniciGoruntuleActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(intent);
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                // Veri ekleme hatası

                                                                Toast.makeText(RegisterActivity.this,"Veri ekleme hatası: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                                            });



                                                    FirebaseUser user = auth.getCurrentUser();
                                                    // Oluşturulan kullanıcının bilgilerini kullanabilirsiniz
                                                } else {
                                                    // Yeni kullanıcı oluşturma başarısız
                                                    Toast.makeText(RegisterActivity.this,"Kayıt olma işlemi Başarısız.",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                // Hata durumu
                                Log.e("TAG", "Hata: " + task.getException());
                            }
                        });
            }}
        });
    }
}