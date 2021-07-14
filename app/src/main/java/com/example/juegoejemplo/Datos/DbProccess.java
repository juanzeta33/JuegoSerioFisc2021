package com.example.juegoejemplo.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.juegoejemplo.Entidades.Partida;
import com.example.juegoejemplo.Entidades.Usuarios;
import com.example.juegoejemplo.R;

import java.util.ArrayList;
import java.util.List;

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

    public List<Partida> ObtenerPartidaById(int partida){
        try{
            SQLiteDatabase db = _helper.getReadableDatabase();
            List<Partida> partidas = new ArrayList<>();
            if (db != null){
                String[] campos = {"partida","jugador","juego","nivel","pregunta","respuestas","puntaje","fecha","hora"};
                Cursor cursor = db.query("partida",campos,"partida="+partida,null,null,null,"hora DESC");
                if (cursor.moveToFirst()){
                    do{
                        Partida part = new Partida();
                        part.setPartida(cursor.getInt(0));
                        part.setJugador(cursor.getString(1));
                        part.setJuego(cursor.getString(2));
                        part.setNivel(cursor.getString(3));
                        part.setPregunta(cursor.getString(4));
                        part.setRespuestas(cursor.getString(5));
                        part.setPuntaje(cursor.getInt(6));
                        part.setFecha(cursor.getString(7));
                        part.setHora(cursor.getString(8));

                        partidas.add(part);

                    }while(cursor.moveToNext());
                }
            }
            return partidas;
        }catch (Exception e){
            int x = 1;
        }

        return null;
    }

    public int ObtenerSiguientePartida(String juego){
        int partida = 1;
        try{
           SQLiteDatabase db = _helper.getReadableDatabase();
            if (db != null){
                String[] campo = {"partida"};

                Cursor cursor = db.query("partida",campo,"juego='"+juego+"'",null,"partida",null,"partida DESC","1");
                cursor.moveToFirst();
                do {
                    partida = cursor.getInt(0) + 1;
                }while(cursor.moveToNext());

                return partida;
            }
        }catch (Exception e){
            int x = 1;
        }
        return partida;
    }

    public Usuarios ValidarExistenciaUsuario(Usuarios usuario){
        try{
            SQLiteDatabase db = _helper.getReadableDatabase();
            if (db != null){
                String[] campos = new String[]{"id","user","password"};
                String[] arg = new String[]{usuario.getUser(),usuario.getPassword()};
                Cursor cursor = db.query("users",campos,"user='"+arg[0]+"' AND password ='"+arg[1]+"'",null,null,null,null);
                if (cursor.moveToFirst()){
                    Usuarios session = new Usuarios(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            ""
                    );
                    GuardarSessionUsuario(session);
                    return session;
                }
            }
        }
        catch (Exception c){
            return  null;}
        return null;
    }

    public Boolean GuardarSessionUsuario(Usuarios usuario){
        try{
            SQLiteDatabase db = _helper.getWritableDatabase();
            if (db != null){
                db.delete("session",null,null);
                ContentValues values = new ContentValues();
                values.put("id",usuario.getId());
                values.put("user",usuario.getUser());
                values.put("nombre",usuario.getNombre());

                db.insert("session",null,values);
                db.close();
                return true;
            }
        }
        catch (Exception e){}
        return false;
    }

    public Usuarios ObtenerUsuarioSession(){
        try{
            SQLiteDatabase db = _helper.getReadableDatabase();
            if (db != null){
                String[] campos = new String[]{"id","user","nombre"};
                Cursor cursor = db.query("session",campos,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    Usuarios session = new Usuarios(
                            cursor.getInt(0),
                            cursor.getString(1),
                            "",
                            cursor.getString(2)
                    );
                    return session;
                }
            }
        }
        catch (Exception c){
            return  null;}
        return null;
    }

    public void CerrarSession(){
        try{
            SQLiteDatabase db = _helper.getWritableDatabase();
            if (db != null){
                db.delete("session",null,null);
                db.close();
            }
        }
        catch (Exception x){}
    }
}
