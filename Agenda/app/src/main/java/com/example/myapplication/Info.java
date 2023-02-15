package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    TextView nombre_completo, dir, mail, tlf, obser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //TextViews del layout
        nombre_completo = findViewById(R.id.user);
        mail = findViewById(R.id.mail);
        dir = findViewById(R.id.dir);
        tlf = findViewById(R.id.telef);
        obser = findViewById(R.id.info);

        //Recogida de los parámetros del anterior Activity
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String nombre = parametros.getString("nombre");
            String apellido = parametros.getString("apellido");
            String direccion = parametros.getString("direccion");
            String email = parametros.getString("email");
            String telefono = parametros.getString("telefono");
            String observaciones = parametros.getString("observaciones");

            //Aplicamos los parámetros recibidos a los TextViews
            nombre_completo.setText(nombre+" "+apellido);
            dir.setText(direccion);
            mail.setText(email);
            tlf.setText(telefono);
            obser.setText(observaciones);
        }

    }
}