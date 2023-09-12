package com.example.mystockss.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.example.mystockss.R;

import java.util.Calendar;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity {
    CalendarView baslangicCalendarView,bitisCalendarView;
    Button baslangicBtn,bitisBtn;
    Date baslangicDate,bitisDate;
    LinearLayout baslangicLinearLayout,bitisLinearLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        baslangicCalendarView = findViewById(R.id.baslangicCalendarView);
        bitisCalendarView = findViewById(R.id.bitisCalendarView);
        baslangicBtn = findViewById(R.id.baslangicTarihiBtn);
        bitisBtn = findViewById(R.id.bitisTarihiBtn);
        baslangicLinearLayout = findViewById(R.id.baslangicSecLinearLayout);
        bitisLinearLayout = findViewById(R.id.bitisLinearLayout);

        baslangicDate = new Date();
        bitisDate = new Date();



        baslangicCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(Calendar.YEAR, i);
                selectedCalendar.set(Calendar.MONTH, i1);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, i2-1);
                baslangicDate = selectedCalendar.getTime();
            }
        });
        baslangicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TranslateAnimation animation = new TranslateAnimation(+baslangicLinearLayout.getWidth(), 0, 0, 0);
                animation.setDuration(500); // Animasyon süresi, isteğe bağlı olarak ayarlayabilirsiniz
                animation.setInterpolator(new AccelerateDecelerateInterpolator()); // İnterpolasyon ayarları


                baslangicLinearLayout.startAnimation(animation);

                baslangicLinearLayout.setVisibility(View.GONE);
                bitisLinearLayout.setVisibility(View.VISIBLE);

            }
        });
        bitisCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(Calendar.YEAR, i);
                selectedCalendar.set(Calendar.MONTH, i1);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, i2);
                bitisDate = selectedCalendar.getTime();
            }
        });

        bitisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalenderActivity.this,logActivity.class);
                intent.putExtra("startDate",baslangicDate.getTime());
                intent.putExtra("endDate",bitisDate.getTime());
                startActivity(intent);

            }
        });
    }
}