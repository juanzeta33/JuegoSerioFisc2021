package com.example.juegoejemplo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juegoejemplo.Entidades.Juego;
import com.example.juegoejemplo.Entidades.Partida;
import com.example.juegoejemplo.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResumenListViewAdapter extends ArrayAdapter<Partida> {

    List<Partida> partidas = new ArrayList<>();

    public ResumenListViewAdapter(Context context, List<Partida> datos){
        super(context, R.layout.listview_resumen,datos);
        partidas = datos;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public View getView(int position, View v, ViewGroup vg){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_resumen, null);

        TextView lblPregunta = (TextView)item.findViewById(R.id.lslblPregunta);
        lblPregunta.setText(partidas.get(position).getPregunta());

        TextView lblRespuesta = (TextView)item.findViewById(R.id.lslblRespuesta);
        lblRespuesta.setText(partidas.get(position).getRespuestas());

        TextView lblPuntaje = (TextView)item.findViewById(R.id.lslblPuntaje);
        lblPuntaje.setText(Integer.toString(partidas.get(position).getPuntaje()));

        TextView lblHora = (TextView)item.findViewById(R.id.lslblHora);
        lblHora.setText(partidas.get(position).getHora());

        return item;
    }
}
