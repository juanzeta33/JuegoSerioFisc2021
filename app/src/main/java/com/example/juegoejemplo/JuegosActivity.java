package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.juegoejemplo.Adapters.JuegosListViewApdater;
import com.example.juegoejemplo.Entidades.Juego;
import com.example.juegoejemplo.Services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JuegosActivity extends AppCompatActivity {

    ListView lstJuegos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);

        lstJuegos = (ListView)findViewById(R.id.lstJuegos);
        LoadListview();
    }

    private void LoadListview() {
        Call<List<Juego>> response = ApiService.getApiService().getAllJuegos();
        response.enqueue(new Callback<List<Juego>>() {
            @Override
            public void onResponse(Call<List<Juego>> call, Response<List<Juego>> response) {
                if (response.isSuccessful()){
                    List<Juego> juegos = response.body();
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
}