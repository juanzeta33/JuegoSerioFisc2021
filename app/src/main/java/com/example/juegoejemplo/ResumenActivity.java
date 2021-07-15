package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juegoejemplo.Adapters.ResumenListViewAdapter;
import com.example.juegoejemplo.Datos.DbProccess;
import com.example.juegoejemplo.Entidades.Partida;
import com.example.juegoejemplo.Requests.PartidaRequest;
import com.example.juegoejemplo.Services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumenActivity extends AppCompatActivity {

    ListView lstResumen;
    TextView nivel,juego,jugador,fecha,puntaje,partida;

    List<Partida> _partidas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        int partida = getIntent().getIntExtra("Partida",0);

        InicializarControles();
        LoadListView(partida);
        MapearCampos();
        GuardarPartidaApi(_partidas);
    }

    private void GuardarPartidaApi(List<Partida> partidas) {
        PartidaRequest request = new PartidaRequest();
        request.setJugador(partidas.get(0).getJugador());
        request.setJuego(partidas.get(0).getJuego());
        request.setFecha(partidas.get(0).getFecha());
        request.setHora(partidas.get(0).getHora());
        request.setNivel(partidas.get(0).getNivel());
        request.setPartida(partidas.get(0).getPartida());
        request.setPuntaje(ObtenerPuntaje(partidas));
        request.setDetalle(partidas);

        Call<Integer> response = ApiService.getApiService().postRegistrarPartida(request);
        response.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    int registrado = response.body();
                    if (registrado > 0){

                    }
                }else{
                    int x = 1;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                int x = 1;
            }
        });

    }

    public void RegresarJuegos(View v){
        startActivity(new Intent(getApplicationContext(),JuegosActivity.class));
    }

    private void MapearCampos() {
        nivel.setText(_partidas.get(0).getNivel());
        juego.setText(_partidas.get(0).getJuego());
        jugador.setText(_partidas.get(0).getJugador());
        fecha.setText(_partidas.get(0).getFecha());
        puntaje.setText(Integer.toString(ObtenerPuntaje(_partidas)));
        partida.setText(Integer.toString(_partidas.get(0).getPartida()));
    }

    private int ObtenerPuntaje(List<Partida> partidas){
        int puntaje = 0;
        for (Partida part : partidas){
            puntaje = puntaje + part.getPuntaje();
        }

        return puntaje;
    }

    private void LoadListView(int partida) {
        DbProccess db = new DbProccess(getApplicationContext());

        _partidas = db.ObtenerPartidaById(partida);

        ResumenListViewAdapter adapter =
                new ResumenListViewAdapter(getApplicationContext(),_partidas);

        lstResumen.setAdapter(adapter);
    }

    private void InicializarControles(){
        lstResumen = (ListView)findViewById(R.id.lstResumen);

        nivel = (TextView)findViewById(R.id.txtNivel);
        juego = (TextView)findViewById(R.id.txtJuego);
        jugador = (TextView)findViewById(R.id.txtJugador);
        fecha = (TextView)findViewById(R.id.txtFecha);
        puntaje = (TextView)findViewById(R.id.txtPuntos);
        partida = (TextView)findViewById(R.id.txtPartida);
    }
}