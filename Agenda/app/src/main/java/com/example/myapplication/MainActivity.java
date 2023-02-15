package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private ImageView randomm;
    private ArrayList<String> contactos;
    private ArrayList<Contactos> objetos;
    int count;
    private String[] nombres;
    private Toolbar tolbar;
    private Spinner cmbToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creación del ArrayList
        contactos = new ArrayList<String>();
        objetos = new ArrayList<Contactos>();

        //Definiciones
        randomm = (ImageView) findViewById(R.id.btnrandom);
        listview = (ListView) findViewById(R.id.listview);
        tolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        setSupportActionBar(tolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        cmbToolbar=new Spinner(this);

        //Opciones del menú
        String datos[] = {"Menú","Añadir","Eliminar","Ajustes","Salir"};

        //Datos de Spinner y lo aplicamos al adaptador
        Spinner cmbToolbar = (Spinner) findViewById(R.id.spinner_nav_sub);
        ArrayAdapter<String>adaptador = new ArrayAdapter<>(this,R.layout.appbar_filter_title, datos);
        adaptador.setDropDownViewResource(R.layout.appbar_filter_list);
        cmbToolbar.setAdapter(adaptador);
        //Desabilitamos la primera opción para que nada más iniciar al app no se vaya por aquí
        cmbToolbar.setSelection(0,false);

        //Guardado de todos los nombres
        nombres = new String[]{"Antonio Crespo", "Xandre Martínez", "Iván Cernadas", "Adán Alonso",
                "Aitana Rodríguez", "Dani Rivas", "David Domínguez", "Javier Loureiro", "Miguel Ángel",
                "Naila Álvarez", "Pedro Armada", "Sergio Fernández", "Yessica Rodríguez", "Óscar López"};

        //Creación de los objetos de todos los contactos
        Contactos antonio = new Contactos(1,"Antonio","Crespo","666778899","antoniocrespo@gmail.com","Cesantes","Guapísimo");
        Contactos xandre = new Contactos(2,"Xandre","Martínez","666778899","xandre@gmail.com","Moscoso","Guapísimo");
        Contactos iván = new Contactos(3,"Iván","Cernadas","666778899","ivan@gmail.com","Pousiño","Guapísimo");
        Contactos adán = new Contactos(4,"Adán","Alonso","666778899","adan@gmail.com","Vigo","Guapísimo");
        Contactos aitana = new Contactos(5,"Aitana","Rodríguez","666778899","aitana@gmail.com","Vigo","Guapísimo");
        Contactos daniel = new Contactos(6,"Daniel","Rivas","666778899","daniel@gmail.com","Vigo","Guapísimo");
        Contactos david = new Contactos(7,"David","Domínguez","666778899","david@gmail.com","Vigo","Guapísimo");
        Contactos javier = new Contactos(8,"Javier","Loureiro","666778899","javier@gmail.com","Vigo","Guapísimo");
        Contactos miguel = new Contactos(9,"Miguel","Ángel","666778899","miguel@gmail.com","Vigo","Guapísimo");
        Contactos naila = new Contactos(10,"Naila","Álvarez","666778899","naila@gmail.com","Vigo","Guapísimo");
        Contactos pedro = new Contactos(11,"Pedro","Armada","666778899","pedro@gmail.com","Vigo","Guapísimo");
        Contactos sergio = new Contactos(12,"Sergio","Fernández","666778899","sergio@gmail.com","Redondela","Guapísimo");
        Contactos yessica = new Contactos(13,"Yessica","Rodríguez","666778899","yessica@gmail.com","Vigo","Guapísimo");
        Contactos óscar = new Contactos(14,"Óscar","López","666778899","oscar@gmail.com","Vigo","Guapísimo");

        //Añadimos todos los objetos a l arraylist
        objetos.add(antonio);
        objetos.add(xandre);
        objetos.add(iván);
        objetos.add(adán);
        objetos.add(aitana);
        objetos.add(daniel);
        objetos.add(david);
        objetos.add(javier);
        objetos.add(miguel);
        objetos.add(naila);
        objetos.add(pedro);
        objetos.add(sergio);
        objetos.add(óscar);

        //Rellenamos el ArrayList contactos para visualizarlo en el listView
        for(int i = 0; i<objetos.size(); i++){
            contactos.add(objetos.get(i).getNombre()+" "+objetos.get(i).getApellidos());
        }

        //Aplicamos el adpter
        GalleryAdapter adapter = new GalleryAdapter(this, android.R.layout.activity_list_item, contactos);
        listview.setAdapter(adapter);

        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String select = String.valueOf(cmbToolbar.getSelectedItem());

                switch(select){
                    case "Añadir":

                        // Creamos el alert builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Info Contactos");

                        // Aplicamos el layout
                        final View customLayout = getLayoutInflater().inflate(R.layout.custom_add_contact,null);
                        builder.setView(customLayout);

                        // Añadimos el botón
                        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Obtenemos todos los campos que aparecen en el AlertDialog
                                EditText et_nombre = customLayout.findViewById(R.id.editText_nombre);
                                EditText et_apellidos = customLayout.findViewById(R.id.editText_apellidos);
                                EditText et_numero = customLayout.findViewById(R.id.editText_telefono);
                                EditText et_email = customLayout.findViewById(R.id.editText_email);
                                EditText et_direccion = customLayout.findViewById(R.id.editText_direccion);
                                EditText et_observaciones = customLayout.findViewById(R.id.editText_observaciones);

                                //Vemos si están vacíos o no
                                if(et_nombre.getText().toString().isEmpty() || et_apellidos.getText().toString().isEmpty() ||
                                et_numero.getText().toString().isEmpty() || et_email.getText().toString().isEmpty() ||
                                et_direccion.getText().toString().isEmpty() || et_observaciones.getText().toString().isEmpty()){
                                    //Mensaje de que no se va a crear un contacto si faltan datos
                                    Toast.makeText(MainActivity.this, "Faltan Datos, Contacto no Creado", Toast.LENGTH_LONG).show();
                                }else {
                                    //si está todo creamos el objeto con lo parámetros que reyenó el user
                                    Contactos c = new Contactos(contactos.size() + 1, et_nombre.getText().toString()
                                            , et_apellidos.getText().toString(), et_numero.getText().toString(), et_email.getText().toString(),
                                            et_direccion.getText().toString(), et_observaciones.getText().toString());

                                    //Borramos el ArrayList
                                    contactos.clear();

                                    //Añadimos el nuevo objeto
                                    objetos.add(c);

                                    //Y lo rellenamos
                                    for(int i = 0; i<objetos.size(); i++){
                                        contactos.add(objetos.get(i).getNombre()+" "+objetos.get(i).getApellidos());
                                    }

                                    //Aplicamos el adaptador al ListView
                                    GalleryAdapter adapter = new GalleryAdapter(MainActivity.this, android.R.layout.activity_list_item, contactos);
                                    listview.setAdapter(adapter);
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        cmbToolbar.setSelection(0);

                        break;
                    case "Eliminar":
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                        builder2.setTitle("Nombre del Contacto");

                        // set the custom layout
                        final View customLayout2 = getLayoutInflater().inflate(R.layout.custom_delete_contact,null);
                        builder2.setView(customLayout2);

                        //Definimos el autocomplete
                        AutoCompleteTextView et_contact = customLayout2.findViewById(R.id.editText_contact);

                        //Lo Aplicamos
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (MainActivity.this, android.R.layout.simple_dropdown_item_1line, contactos);
                        et_contact.setThreshold(1);
                        et_contact.setAdapter(adapter);

                        //Cuando se hace click en el ok
                        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nombre_completo = et_contact.getText().toString();
                                String nombre_solo="";

                                if(nombre_completo.contains(" ")){
                                    nombre_solo= nombre_completo.substring(0, nombre_completo.indexOf(" "));
                                }
                                for(int i = 0 ; i<objetos.size();i++){
                                    if(objetos.get(i).getNombre().equalsIgnoreCase(nombre_solo)){
                                        objetos.remove(i);
                                    }
                                }
                                //Borramos el ArrayList
                                contactos.clear();

                                //Y lo rellenamos
                                for(int i = 0; i<objetos.size(); i++){
                                    contactos.add(objetos.get(i).getNombre()+" "+objetos.get(i).getApellidos());
                                }

                                //Aplicamos el adaptador al ListView
                                GalleryAdapter adapter = new GalleryAdapter(MainActivity.this, android.R.layout.activity_list_item, contactos);
                                listview.setAdapter(adapter);
                            }
                        });
                        AlertDialog dialog2 = builder2.create();
                        dialog2.show();
                        cmbToolbar.setSelection(0);
                        break;
                    case "Ajustes":
                        //Intent para ir a los ajustes del dispositivo
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        cmbToolbar.setSelection(0);
                        break;
                    case "Salir":
                        //Cerrar la app
                        metodoCerrar();
                        cmbToolbar.setSelection(0);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "nada", Toast.LENGTH_LONG).show();
            }
        });

        randomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Borramos el ArrayList
                contactos.clear();

                //Y lo rellenamos por si existen nuevos contactos
                for(int i = 0; i<objetos.size(); i++){
                    contactos.add(objetos.get(i).getNombre()+" "+objetos.get(i).getApellidos());
                }

                //Randomizamos el ArrayList
                Collections.shuffle(contactos, new Random());

//              Aplicamos el adaptador al ListView
                GalleryAdapter adapter = new GalleryAdapter(MainActivity.this, android.R.layout.activity_list_item, contactos);
                listview.setAdapter(adapter);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String nombre_objeto="";
                String sentence = contactos.get(position);

                //Vemos si tiene un espacio
                if(sentence.contains(" ")){
                    //Cogemos solo la primera palabra
                    nombre_objeto= sentence.substring(0, sentence.indexOf(" "));
                }
                for(int i = 0; i < objetos.size();i++){
                    if(objetos.get(i).getNombre().equals(nombre_objeto)){

                        //Definición de las variables
                        String nombre = objetos.get(i).getNombre();
                        String apellido = objetos.get(i).getApellidos();
                        String direcion = objetos.get(i).getDireccion();
                        String email = objetos.get(i).getEmail();
                        String telefono = objetos.get(i).getTelefono();
                        String observaciones = objetos.get(i).getObservaciones();

                        //Las mandamos a la Activity Info
                        Intent intent = new Intent(MainActivity.this, Info.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("nombre",nombre);
                        intent.putExtra("apellido",apellido);
                        intent.putExtra("direccion",direcion);
                        intent.putExtra("email",email);
                        intent.putExtra("telefono",telefono);
                        intent.putExtra("observaciones",observaciones);
                        startActivity(intent);
                    }
                }
            }
        });
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