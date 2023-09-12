package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mystockss.R;

public class CallActivity extends AppCompatActivity {
    ImageView mailAt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        mailAt = findViewById(R.id.mailAtImageView);

        String email = "fatihaytar06@gmail.com";

        mailAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + email));
                startActivity(intent);
            }
        });



    }
}