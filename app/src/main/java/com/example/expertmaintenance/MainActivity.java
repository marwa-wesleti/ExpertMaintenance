package com.example.expertmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView btnClient,btnContrat;
    SQLiteDatabase db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClient=(CircleImageView) findViewById(R.id.btnClient);
        btnContrat=(CircleImageView) findViewById(R.id.btnContrat);

        db=openOrCreateDatabase("EXPERT_MAINTENANCE",MODE_PRIVATE,null);
        Glide.with(MainActivity.this)
                .load(R.drawable.contrat)
                .centerCrop()
                .into(btnContrat);
        Glide.with(MainActivity.this)
                .load(R.drawable.client)
                .centerCrop()
                .into(btnClient);



        btnContrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ContratActivity.class));
            }
        });
        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClientActivity.class));
            }
        });



    }
}