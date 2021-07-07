package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.juegoejemplo.Entidades.Estudiante;
import com.example.juegoejemplo.R;
import com.example.juegoejemplo.Responses.Facultad;
import com.example.juegoejemplo.Services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    List<Facultad> _facultades;
    Spinner spnFacultades;
    EditText nombre,cedula,edad,correo,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        InicializarControles();
        LoadSpinner();
    }

    private void LoadSpinner() {
        Call<List<Facultad>> response = ApiService.getApiService().getAllFacultades();
        response.enqueue(new Callback<List<Facultad>>() {
            @Override
            public void onResponse(Call<List<Facultad>> call, Response<List<Facultad>> response) {
                if (response.isSuccessful()){
                    _facultades = response.body();
                    List<String> facultadesListString = new ArrayList<String>();
                    for(Facultad facultad : _facultades){
                        facultadesListString.add(facultad.getFacultad());
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,facultadesListString);

                    spnFacultades.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Facultad>> call, Throwable t) {

            }
        });
    }

    private void InicializarControles() {
        spnFacultades = (Spinner)findViewById(R.id.spnFacultades);

        nombre = (EditText)findViewById(R.id.txtNombre);
        cedula = (EditText)findViewById(R.id.txtCedula);
        edad = (EditText)findViewById(R.id.txtEdad);
        correo = (EditText)findViewById(R.id.txtCorreo);
        password = (EditText)findViewById(R.id.txtPassword);
    }

    public void RegistrarEstudiante(View v){
        try {
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre_completo(nombre.getText().toString());
            estudiante.setCedula(cedula.getText().toString());
            estudiante.setEdad(edad.getText().toString());
            estudiante.setEmail(correo.getText().toString());
            estudiante.setPassword(password.getText().toString());

            String selectedFac = spnFacultades.getSelectedItem().toString();
            String facultadId = "";
            for(Facultad facultad : _facultades){
                if (facultad.getFacultad().equals(selectedFac)){
                    facultadId = facultad.getId();
                }
            }
            estudiante.setFacultad(facultadId);

            Call<Integer> response = ApiService.getApiService().postRegistrarEstudiante(estudiante);
            response.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()){
                        int x = 1;
                    }else{
                        int x = 1;
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    int x = 1;
                }
            });

        }catch (Exception e){
            int x= 1;
        }
    }
}