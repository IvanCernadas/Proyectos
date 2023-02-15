package com.example.myapplication;

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

        //Definición
        ImageView fondo;

        //Imágenes
        fondo = findViewById(R.id.fondo);

        //Timer para pasar de activity
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

        //Lo que pasa si el usuario hace click en el fondo
        fondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.cancel();//Se cancela el timer si el user hace click
                Intent inton = new Intent(Launcher.this, MainActivity.class);
                startActivity(inton);
            }
        });
    }
}