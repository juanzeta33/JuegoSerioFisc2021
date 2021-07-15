package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.juegoejemplo.Adapters.TablaListViewAdapter;
import com.example.juegoejemplo.Entidades.Tabla;
import com.example.juegoejemplo.Services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablaActivity extends AppCompatActivity {

    ListView lstTabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);

        InicializarControles();
        LoadListView();
    }

    private void LoadListView() {
        Call<List<Tabla>> response = ApiService.getApiService().getAllTable();
        response.enqueue(new Callback<List<Tabla>>() {
            @Override
            public void onResponse(Call<List<Tabla>> call, Response<List<Tabla>> response) {
                if (response.isSuccessful()){
                    List<Tabla> table = response.body();
                    TablaListViewAdapter adapter = new TablaListViewAdapter(getApplicationContext(),table);
                    lstTabla.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Tabla>> call, Throwable t) {

            }
        });
    }

    private void InicializarControles() {
        lstTabla = (ListView)findViewById(R.id.lstTabla);
    }
}