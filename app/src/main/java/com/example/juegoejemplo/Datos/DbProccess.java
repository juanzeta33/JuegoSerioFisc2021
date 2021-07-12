package com.example.juegoejemplo.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.juegoejemplo.Entidades.Partida;
import com.example.juegoejemplo.R;

public class DbProccess {

    JuegosDataHelper _helper;

    public DbProccess(Context context){
        _helper = new JuegosDataHelper(context,"juegos",null, R.integer.dbVersion);
    }

    public long InsentarRespuestaPartida(Partida partida,int numPartida){
        try{
            SQLiteDatabase db = _helper.getWritableDatabase();
            if (db != null){
                ContentValues values = new ContentValues();
                values.put("partida",numPartida);
                values.put("jugador",partida.getJugador());
                values.put("juego",partida.getJuego());
                values.put("nivel",partida.getNivel());
                values.put("pregunta",partida.getPregunta());
                values.put("respuestas",partida.getRespuestas());
                values.put("puntaje",partida.getPuntaje());
                values.put("fecha",partida.getFecha());
                values.put("hora",partida.getHora());

                return db.insert("partida",null,values);
            }
        }catch (Exception e){
            int x = 1;
        }
        return 0;
    }
}
