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
import com.example.juegoejemplo.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JuegosListViewApdater extends ArrayAdapter<Juego> {
    List<Juego> juegos = new ArrayList<>();

    public JuegosListViewApdater(Context context, List<Juego> datos){
        super(context, R.layout.listview_juegos,datos);
        juegos = datos;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public View getView(int position, View v, ViewGroup vg){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_juegos, null);

        TextView lblNombre = (TextView)item.findViewById(R.id.lblNombre);
        lblNombre.setText(juegos.get(position).getNombre());

        TextView lblCodigo = (TextView)item.findViewById(R.id.lblCodigo);
        lblCodigo.setText(juegos.get(position).getCodigo());

        TextView lblResumen = (TextView)item.findViewById(R.id.lblResumen);
        lblResumen.setText(juegos.get(position).getResumen());

        ImageView imv = (ImageView)item.findViewById(R.id.foto);

        try{
            URL url = new URL(juegos.get(position).getImagen());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imv.setImageBitmap(bmp);
        }
        catch (Exception e){}

        return item;
    }
}
