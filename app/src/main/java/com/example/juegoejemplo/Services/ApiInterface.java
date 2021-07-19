package com.example.juegoejemplo.Services;

import com.example.juegoejemplo.Entidades.Juego;
import com.example.juegoejemplo.Entidades.Estudiante;
import com.example.juegoejemplo.Entidades.Preguntas;
import com.example.juegoejemplo.Entidades.Tabla;
import com.example.juegoejemplo.Requests.PartidaRequest;
import com.example.juegoejemplo.Responses.Facultad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api.php?ep=login")
    Call<Estudiante> getEstudianteLogin(@Query("u") String u, @Query("p") String p);

    @GET("api.php?ep=juegos")
    Call<List<Juego>> getAllJuegos();

    @GET("api.php?ep=facultades")
    Call<List<Facultad>> getAllFacultades();

    @POST("api.php?ep=estudiantesSave")
    Call<Integer> postRegistrarEstudiante(@Body Estudiante estudiante);

    @POST("api.php?ep=partidaSave")
    Call<Integer> postRegistrarPartida(@Body PartidaRequest partida);

    @GET("api.php?ep=preguntas")
    Call<List<Preguntas>> getPreguntas(@Query("j") int juego);

    @GET("api.php?ep=posiciones")
    Call<List<Tabla>> getAllTable();
}
