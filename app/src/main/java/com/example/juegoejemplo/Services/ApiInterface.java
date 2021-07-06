package com.example.juegoejemplo.Services;

import com.example.juegoejemplo.Entidades.Juego;
import com.example.juegoejemplo.Responses.Estudiante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api.php?ep=login")
    Call<Estudiante> getEstudianteLogin(@Query("u") String u, @Query("p") String p);

    @GET("api.php?ep=juegos")
    Call<List<Juego>> getAllJuegos();
}
