package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.juegoejemplo.Adapters.JuegosListViewApdater;
import com.example.juegoejemplo.Entidades.Juego;
import com.example.juegoejemplo.Services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JuegosActivity extends AppCompatActivity {

    ListView lstJuegos;

    List<Juego> _juegos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);

        lstJuegos = (ListView)findViewById(R.id.lstJuegos);
        LoadListview();
        AttachEvents();
    }

    private void AttachEvents() {
        lstJuegos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String juego = _juegos.get(position).getNombre();
                int juegoId = Integer.parseInt(_juegos.get(position).getId());
                Intent i = new Intent(getApplicationContext(),PartidaActivity.class);
                i.putExtra("Juego",juego);
                i.putExtra("JuegoID",juegoId);
                startActivity(i);
            }
        });
    }

    private void LoadListview() {
        Call<List<Juego>> response = ApiService.getApiService().getAllJuegos();
        response.enqueue(new Callback<List<Juego>>() {
            @Override
            public void onResponse(Call<List<Juego>> call, Response<List<Juego>> response) {
                if (response.isSuccessful()){
                    List<Juego> juegos = response.body();
                    _juegos = juegos;
                    JuegosListViewApdater adapter = new JuegosListViewApdater(getApplicationContext(),juegos);
                    lstJuegos.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Juego>> call, Throwable t) {
                int x = 1;
            }
        });

    }

    public void VerTabla(View v){
        startActivity(new Intent(getApplicationContext(),TablaActivity.class));
    }
}