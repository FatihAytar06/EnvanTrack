package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystockss.R;
import com.squareup.picasso.Picasso;

public class StokActivity extends AppCompatActivity {
    ImageView imageView;
    TextView stokAdedi,envanterAdi;
    Button stokEkleBtn,stokCikarBtn,talepEtBtn;
    String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        imageView = findViewById(R.id.stokActivityImageView);
        stokAdedi = findViewById(R.id.stokActivityStokAdediTextView);
        envanterAdi = findViewById(R.id.stokActivityEnvanterAdiTextView);
        stokEkleBtn = findViewById(R.id.stokEkleBtn);
        stokCikarBtn = findViewById(R.id.stokCikarBtn);
        talepEtBtn = findViewById(R.id.talepEtBtn);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int permissionLevel = sharedPreferences.getInt("permissionLevel", 0);

        if (permissionLevel == 1) {
            stokEkleBtn.setVisibility(View.GONE);
            stokCikarBtn.setVisibility(View.GONE);
            talepEtBtn.setVisibility(View.VISIBLE);
        }


        String resimUri = getIntent().getStringExtra("resUri");
        String envanterAd = getIntent().getStringExtra("stokAdi");
        String stokAdet = getIntent().getStringExtra("stokAdet");
        docId = getIntent().getStringExtra("docId");

        if (resimUri != null&&envanterAd !=null && stokAdet !=null) {
            // Resmi indirin ve ImageView'a yükleyin
            Picasso.get().load(resimUri).into(imageView);
            stokAdedi.setText("Stok adedi : "+stokAdet);
            envanterAdi.setText("Envanter adı : "+envanterAd);
        }
        stokEkleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StokActivity.this,stokEkleActivity.class);
                intent.putExtra("resUri",resimUri);
                intent.putExtra("envanterAdi",envanterAd);
                intent.putExtra("stokAdet",stokAdet);
                intent.putExtra("docId",docId);

                startActivity(intent);
            }
        });
        talepEtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StokActivity.this,TalepolusturActivity.class);
                intent.putExtra("resUri",resimUri);
                intent.putExtra("envanterAdi",envanterAd);
                intent.putExtra("stokAdet",stokAdet);
                intent.putExtra("docId",docId);

                startActivity(intent);
            }
        });
        stokCikarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StokActivity.this,DetailsActivity.class);
                intent.putExtra("resUri",resimUri);
                intent.putExtra("envanterAdi",envanterAd);
                intent.putExtra("stokAdet",stokAdet);
                intent.putExtra("docId",docId);

                startActivity(intent);
            }
        });

    }
}