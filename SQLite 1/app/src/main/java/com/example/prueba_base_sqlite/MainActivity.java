package com.example.prueba_base_sqlite;

import static android.view.View.VISIBLE;
import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String nombreBD = "base_de_datos_alumnos";
    private ArrayList<String> alumns;
    private ArrayList<String> locations;
    private ArrayList<Alumno> list;
    private ListView list1;
    private AutoCompleteTextView busq_dir;
    private ImageView lupa, exit, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ocultar toolbar
        getSupportActionBar().hide();

        //Definiciones
        alumns = new ArrayList<String>();
        locations = new ArrayList<String>();
        list1 = findViewById(R.id.list);
        busq_dir = findViewById(R.id.autoCompleteTextView);
        lupa = findViewById(R.id.lupita);
        exit = findViewById(R.id.exit);
        reset = findViewById(R.id.reset);

        //Rellenamos el ArrayList locations con las posibles direcciones
        locations.add("Moscoso");
        locations.add("Pousiño");
        locations.add("Vigo");
        locations.add("Redondela");
        locations.add("Teis");

        //Aplicamos la ayuda del autocomplete
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (MainActivity.this, android.R.layout.simple_dropdown_item_1line, locations);
        busq_dir.setThreshold(1);
        busq_dir.setAdapter(adapter2);

        //Si la base de datos está vacía (se inicia por primera vez) la rellenamos
        if(sqlnull("alumnos")) {
            setup();
        }
        //Cargamos mediante una consulta todos los datos
        start();

        //Botón de salir de la activity
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoCerrar();
            }
        });

        //Botón de búsqueda
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dir = busq_dir.getText().toString();
                consultadir(dir);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reseteo para que aparezcan otra vez todos los datos en la lista
                start();
            }
        });
    }

    private void consultadir(String dir) {
        Gestor admin = new Gestor(MainActivity.this, nombreBD, null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        list = new ArrayList<Alumno>();
        if (bd != null) {
            Cursor fila = bd.rawQuery("select codigo,nombre,apellido,direccion,telefono from alumnos where direccion =\""+dir+"\"", null);
            if (fila != null) {
                if(fila.moveToFirst()){
                    do {//bucle para extraer todos lo elementos dados
                        int codigo = fila.getInt(fila.getColumnIndex("codigo"));
                        String nombre = fila.getString(fila.getColumnIndex("nombre"));
                        String apellido = fila.getString(fila.getColumnIndex("apellido"));
                        String direccion = fila.getString(fila.getColumnIndex("direccion"));
                        String telefono = fila.getString(fila.getColumnIndex("telefono"));

                        //creamos objetos con los datos dados
                        Alumno a = new Alumno(nombre, apellido, direccion, telefono);
                        //Y lo añadimos a la lista
                        list.add(a);
                    } while (fila.moveToNext());
                }
                //Borramos todo del ArrayList
                alumns.clear();

                //Rellenamos el ArrayList list para visualizarlo en el listView
                for(int i = 0; i<list.size(); i++){
                    alumns.add("Nombre: "+list.get(i).getNombre()+" "+list.get(i).getApellido()+"\nDirección: "+list.get(i).getDireccion()+
                            "\nTeléfono: "+list.get(i).getTelefono());
                }

                //Aplicamos el adapter
                Adapter adapter = new Adapter(this, android.R.layout.activity_list_item, alumns);
                list1.setAdapter(adapter);
            } else
                Toast.makeText(MainActivity.this, "No existe un cliente con dicho teléfono", Toast.LENGTH_SHORT).show();
            fila.close();
        } else {
            Toast.makeText(this, "La Base de datos no existe", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public boolean sqlnull(String tabla){
        //Comprobamos si la base de datos está vacía
        int count = 0;
        Gestor mDbHelper = new Gestor(MainActivity.this, nombreBD, null, 1);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + tabla, null);
        try {
            if(cursor != null)
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        if(count>0)
            return false;
        else
            return true;
    }
    public void start() {
        Gestor admin = new Gestor(MainActivity.this, nombreBD, null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        list = new ArrayList<Alumno>();
        if (bd != null) {
            Cursor fila = bd.rawQuery("select codigo,nombre,apellido,direccion,telefono from alumnos", null);
            if (fila != null) {
                if(fila.moveToFirst()){
                    do {
                        int codigo = fila.getInt(fila.getColumnIndex("codigo"));
                        String nombre = fila.getString(fila.getColumnIndex("nombre"));
                        String apellido = fila.getString(fila.getColumnIndex("apellido"));
                        String direccion = fila.getString(fila.getColumnIndex("direccion"));
                        String telefono = fila.getString(fila.getColumnIndex("telefono"));

                        Alumno a = new Alumno(nombre, apellido, direccion, telefono);

                        list.add(a);
                    } while (fila.moveToNext());
                }
                //Borramos todo del ArrayList
                alumns.clear();

                //Rellenamos el ArrayList list para visualizarlo en el listView
                for(int i = 0; i<list.size(); i++){
                    alumns.add("Nombre: "+list.get(i).getNombre()+" "+list.get(i).getApellido()+"\nDirección: "+list.get(i).getDireccion()+
                            "\nTeléfono: "+list.get(i).getTelefono());
                }

                //Aplicamos el adapter
                Adapter adapter = new Adapter(this, android.R.layout.activity_list_item, alumns);
                list1.setAdapter(adapter);
            } else
                Toast.makeText(MainActivity.this, "No existe un cliente con dicho teléfono", Toast.LENGTH_SHORT).show();
            fila.close();
        } else {
            Toast.makeText(this, "La Base de datos no existe", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
    private void setup() {
        //Arrays con Strings predefinidos
        String[] nombres = {"Antonio","Xandre","Naila","Iván","Yessica","Sergio","Aitana"};
        String[] apellidos = {"Crespo","Martínez","Álvarez","Cernadas","Rodríguez","Fernandez","Rodríguez"};
        String[] direcciones = {"Cesantes","Moscoso","Teis","Pousiño","Vigo","Redondela","Vigo"};
        String[] telefonos = {"+34-111-11-11-11","+34-222-22-22-22","+34-333-33-33-33","+34-444-44-44-44",
                "+34-555-55-55-55","+34-666-66-66-66","+34-777-77-77-77"};
        int count=0;
        do {
            Gestor admin = new Gestor(MainActivity.this, nombreBD, null, 1);
            SQLiteDatabase bd;
        try {
            bd = admin.getWritableDatabase();
            if (bd != null) {
                    String nombre = nombres[count];
                    String apellido = apellidos[count];
                    String direccion = direcciones[count];
                    String telefono = telefonos[count];
                    Alumno a = new Alumno(nombre,apellido,direccion,telefono);
                    list.add(a);
                    ContentValues registro = new ContentValues();
                    // registro.put("codigo", codigo);
                    registro.put("nombre", nombre);
                    registro.put("apellido", apellido);
                    registro.put("direccion", direccion);
                    registro.put("telefono", telefono);
                    // Toast.makeText(this, fecha, Toast.LENGTH_SHORT).show();
                    bd.insert("alumnos", null, registro);
                    bd.close();
            } else
                Toast.makeText(this, "Base de datos no creada", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException ex) {
            bd = admin.getReadableDatabase();
            Toast.makeText(this, "No se puede escribir la Base de Datos", Toast.LENGTH_SHORT).show();
        }
        bd.close();
        count++;
        }while(count!=6);
    }

    public void metodoCerrar()
    {
        //aquí cerramos el actícity actual
        finish();
        //creamos un nuevo intent de action_main para el cierre de todo lo que esté abierto
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}