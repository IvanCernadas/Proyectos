package com.example.prueba_base_sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> names;
    private ArrayList<Alumno> alum;
    private int layout;
    private LayoutInflater inflater;

    public Adapter(Context context, int layout, ArrayList<String> names) {
        this.context = context;
        this.names = names;
        this.layout = layout;
    }

    public Adapter(MainActivity context, ArrayList<Alumno> list) {
        this.context = context;
        this.alum = list;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.names.size();
    }

    public Object getItem(int position) {
        return this.names.get(position);
    }

    public long getItemId(int id) {
        return id;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Copiamos la vista
        View v = convertView;

        //Inflamos la vista con nuestro propio layout
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        v = layoutInflater.inflate(R.layout.list_item, null);

        // Valor actual según la posición
        String currentName = names.get(position);

        // Referenciamos el elemento a modificar y lo rellenamos
        TextView nombre = (TextView) v.findViewById(R.id.nombre);


        //Devolvemos la vista inflada
        return v;
    }
}
