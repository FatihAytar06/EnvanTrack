package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystockss.Class.Talepler;
import com.example.mystockss.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TalepDetailsActivity extends AppCompatActivity {
    TextView itemAdi,talepMiktari,talepEdenPersonel;
    ImageView talepResim;
    Button talepKaldirBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talepdetails);
        itemAdi = findViewById(R.id.talepDetailsActivityEnvanterAdiTextView);
        talepMiktari = findViewById(R.id.talepDetailsActivityStokAdediTextView);
        talepResim = findViewById(R.id.talepDetailsActivityImageView);
        talepKaldirBtn = findViewById(R.id.talepTamamlaBtn);
        talepEdenPersonel = findViewById(R.id.talepDetailsActivityTalepEdenTextView);

        String resimUri = getIntent().getStringExtra("resUri");
        String talepAdet = getIntent().getStringExtra("talepAdet");
        String stokAdi = getIntent().getStringExtra("stokAdi");
        String talenEden = getIntent().getStringExtra("talenEden");
        String docId = getIntent().getStringExtra("docId");
        Date tarih = (Date) getIntent().getSerializableExtra("talepTarihi");
        Picasso.get().load(resimUri).into(talepResim);
        talepMiktari.setText("Talep edilen adet : "+talepAdet);
        itemAdi.setText("Envanter adı : "+stokAdi);
        talepEdenPersonel.setText("Talep eden : " +talenEden);

        talepKaldirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TalepDetailsActivity.this,DetailsActivity.class);
                intent.putExtra("stokAdet",talepAdet);
                intent.putExtra("resUri",resimUri);
                intent.putExtra("talepEden",talenEden);
                intent.putExtra("talepAdet",talepAdet);
                intent.putExtra("docId",docId);
                intent.putExtra("talepTarihi",tarih);
                intent.putExtra("envanterAdi",stokAdi);
                startActivity(intent);

                //TalepKaldir(talenEden,stokAdi,resimUri);
            }
        });

    }



}