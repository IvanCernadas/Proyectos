package com.example.prueba_base_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //Ocultar toolbar
        getSupportActionBar().hide();

        //Definición
        ImageView logo;

        //Imágenes
        logo = findViewById(R.id.logo);

        final Timer t = new Timer();
        super.onStart();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Launcher.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 2000);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.cancel();
                Intent inton = new Intent(Launcher.this, MainActivity.class);
                startActivity(inton);
            }
        });
    }
}