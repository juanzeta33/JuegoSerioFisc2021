package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juegoejemplo.Datos.DbProccess;
import com.example.juegoejemplo.Entidades.Estudiante;
import com.example.juegoejemplo.Entidades.Usuarios;
import com.example.juegoejemplo.Services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    DbProccess _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InicializarControles();

        _db = new DbProccess(getApplicationContext());

        ValidarSession();

    }

    private void ValidarSession() {
        Usuarios user = _db.ObtenerUsuarioSession();
        if (user != null){
            startActivity(new Intent(getApplicationContext(),JuegosActivity.class));
        }
    }

    private void InicializarControles() {
        txtUser = (EditText)findViewById(R.id.txtUsuario);
        txtPass = (EditText)findViewById(R.id.txtContrasena);
    }

    public void IniciarSession(View v){
        try {
            String user = txtUser.getText().toString();
            String pass = txtPass.getText().toString();

            Call<Estudiante> response = ApiService.getApiService().getEstudianteLogin(user,pass);
            response.enqueue(new Callback<Estudiante>() {
                @Override
                public void onResponse(Call<Estudiante> call, Response<Estudiante> response) {
                    if (response.isSuccessful()){
                        Estudiante estudiante = response.body();
                        if (estudiante != null){

                            Usuarios user =
                                    new Usuarios(
                                            Integer.parseInt(estudiante.getId()),
                                            estudiante.getEmail(),
                                            "",
                                            estudiante.getNombre_completo()
                                    );

                            _db.GuardarSessionUsuario(user);

                            Toast.makeText(getApplicationContext(),"Se loguea coool desde el api",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),JuegosActivity.class));
                        }
                    }else {
                        int x = 1;
                    }
                }

                @Override
                public void onFailure(Call<Estudiante> call, Throwable t) {
                    int x = 1;
                }
            });
        }catch (Exception e){
            int x = 1;
        }
    }

    public void Registrar(View v){
        startActivity(new Intent(getApplicationContext(),RegistroActivity.class));
    }
}