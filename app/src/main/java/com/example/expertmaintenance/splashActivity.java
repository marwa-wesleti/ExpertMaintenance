package com.example.expertmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class splashActivity extends AppCompatActivity {
    TextView titleSplash,recap,recap2;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        titleSplash=(TextView) findViewById(R.id.titleSplash);
        recap=(TextView) findViewById(R.id.recap);
        recap2=(TextView) findViewById(R.id.recap2);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(2000)
                .playOn(titleSplash);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(3000)
                .playOn(recap);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(3000)
                .playOn(recap2);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        },5000);




    }
}