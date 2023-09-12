package com.example.mystockss.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mystockss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView email,password,resetPassword,warning;
    Button girisBtn;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.usernameTextView);
        password = findViewById(R.id.passwordTextView);
        resetPassword = findViewById(R.id.forgotPassword);
        girisBtn = findViewById(R.id.loginBtn);
        warning = findViewById(R.id.warningTextView);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        girisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eposta = email.getText().toString();
                String sifre = password.getText().toString();

                auth.signInWithEmailAndPassword(eposta, sifre)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Giriş başarılı
                                Log.d("TAG", "Giriş başarılı");
                                FirebaseUser user = auth.getCurrentUser();
                                warning.setVisibility(View.VISIBLE);
                                warning.setText("Giriş Başarılı,Hoşgeldiniz...");
                                warning.setTextColor(Color.GREEN);
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                warning.setText("Kullanıcı adı veya şifre yanlış !");
                                warning.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });



    }

}