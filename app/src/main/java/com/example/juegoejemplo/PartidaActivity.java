package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juegoejemplo.Datos.DbProccess;
import com.example.juegoejemplo.Entidades.Partida;
import com.example.juegoejemplo.Entidades.Preguntas;
import com.example.juegoejemplo.Entidades.Respuestas;
import com.example.juegoejemplo.Entidades.Usuarios;
import com.example.juegoejemplo.Services.ApiService;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartidaActivity extends AppCompatActivity {

    List<Preguntas> _preguntas;
    Preguntas _preguntaActual;

    LinearLayout lnRender;
    TextView nivel,tipo,pregunta;

    DbProccess _db;
    int _numPartida = 0;
    String _jugador = "";
    String _juego = "";

    List<String> _selectedCheckboxs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        _db = new DbProccess(getApplicationContext());

        Intent i = getIntent();
        _juego = i.getStringExtra("Juego");
        _numPartida = _db.ObtenerSiguientePartida(_juego);

        ObtenerUsuarioSession();

        InicializarControles();
        ObtenerPreguntas();
    }

    private void ObtenerUsuarioSession() {
        Usuarios user = _db.ObtenerUsuarioSession();
        _jugador = user.getNombre();
    }

    private void ObtenerPreguntas() {
        Call<List<Preguntas>> response = ApiService.getApiService().getPreguntas();
        response.enqueue(new Callback<List<Preguntas>>() {
            @Override
            public void onResponse(Call<List<Preguntas>> call, Response<List<Preguntas>> response) {
                if (response.isSuccessful()){
                    _preguntas = response.body();
                    if (_preguntas.size() > 0){

                        nivel.setText(_preguntas.get(0).getNivel());
                        _preguntaActual = _preguntas.get(0);
                        RenderPrimeraPregunta();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Preguntas>> call, Throwable t) {
                int x = 1;
            }
        });
    }

    private void InicializarControles() {
        lnRender = (LinearLayout)findViewById(R.id.renderRespuestas);

        nivel = (TextView)findViewById(R.id.lblNivel);
        tipo = (TextView)findViewById(R.id.lblTipoPregunta);
        pregunta = (TextView)findViewById(R.id.lblPregunta);
    }

    private void RenderPrimeraPregunta(){
        pregunta.setText(_preguntaActual.getPregunta());
        tipo.setText(_preguntaActual.getTipo());

        if (_preguntaActual.getTipo_pregunta_id().equals("2")){
            RenderPreguntaOpcionMultiple(_preguntaActual);
        }else if(_preguntaActual.getTipo_pregunta_id().equals("3")){
            RenderPreguntaVF(_preguntaActual);
        }else if(_preguntaActual.getTipo_pregunta_id().equals("4")){
            RenderPreguntaMejorOpcion(_preguntaActual);
        }
    }

    private void RenderPregunta(Preguntas preguntaAnterior){
        int indiceActual = _preguntas.indexOf(preguntaAnterior);

        if(indiceActual == _preguntas.size() - 1){
            Intent i = new Intent(getApplicationContext(),ResumenActivity.class);
            i.putExtra("Partida",_numPartida);
            startActivity(i);
        }else{
            _preguntaActual = _preguntas.get(_preguntas.indexOf(preguntaAnterior)+1);

            pregunta.setText(_preguntaActual.getPregunta());
            tipo.setText(_preguntaActual.getTipo());

            if (_preguntaActual.getTipo_pregunta_id().equals("2")){
                RenderPreguntaOpcionMultiple(_preguntaActual);
            }else if(_preguntaActual.getTipo_pregunta_id().equals("3")){
                RenderPreguntaVF(_preguntaActual);
            }else if(_preguntaActual.getTipo_pregunta_id().equals("4")){
                RenderPreguntaMejorOpcion(_preguntaActual);
            }
        }
    }

    private void RenderPreguntaMejorOpcion(Preguntas pregunta) {

        RadioGroup group = new RadioGroup(getApplicationContext());
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        group.setLayoutParams(params);

        for(Respuestas respuesta : pregunta.getRespuestas()){
            RadioButton newCheck = new RadioButton(getApplicationContext());

            newCheck.setLayoutParams(params);
            newCheck.setText(respuesta.getRespuesta());
            newCheck.setId(View.generateViewId());

            group.addView(newCheck);
        }

        lnRender.addView(group);

        Button validar = new Button(getApplicationContext());
        validar.setLayoutParams(params);
        validar.setText("Validar Respuestas");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puntaje = 0;
                RadioButton checkResponse = group.findViewById(group.getCheckedRadioButtonId());
                String selectedResponse = checkResponse.getText().toString();

                for (Respuestas res : pregunta.getRespuestas()){
                    if (res.getRespuesta().equals(selectedResponse) && res.getCorrecta().equals("1")){
                        puntaje = Integer.parseInt(res.getPuntaje());
                    }
                }

                lnRender.removeAllViews();
                RenderPregunta(pregunta);

                GuardarRespuesta(pregunta,selectedResponse,puntaje);
            }
        });

        lnRender.addView(validar);
    }

    private void RenderPreguntaVF(Preguntas pregunta) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button verdadero = new Button(getApplicationContext());
        Button falso = new Button(getApplicationContext());

        verdadero.setLayoutParams(params);
        falso.setLayoutParams(params);

        verdadero.setText("VERDADERO");
        falso.setText("FALSO");

        verdadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int puntaje = 0;
                String respuestas = "";

                if (pregunta.getRespuestas().get(0).getRespuesta().equals("1")){
                    puntaje = Integer.parseInt(pregunta.getRespuestas().get(0).getPuntaje());
                    respuestas = "1";
                    Toast.makeText(getApplicationContext(),"CORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"INCORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                    respuestas = "0";
                }
                GuardarRespuesta(pregunta,respuestas,puntaje);

                lnRender.removeAllViews();
                RenderPregunta(pregunta);
            }
        });

        falso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int puntaje = 0;
                String respuestas = "";

                if (pregunta.getRespuestas().get(0).getRespuesta().equals("0")){
                    puntaje = Integer.parseInt(pregunta.getRespuestas().get(0).getPuntaje());
                    respuestas = "0";
                    Toast.makeText(getApplicationContext(),"CORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }else{
                    respuestas = "1";
                    Toast.makeText(getApplicationContext(),"INCORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }
                GuardarRespuesta(pregunta,respuestas,puntaje);

                lnRender.removeAllViews();
                RenderPregunta(pregunta);
            }
        });

        lnRender.addView(verdadero);
        lnRender.addView(falso);
    }

    private void RenderPreguntaOpcionMultiple(Preguntas pregunta) {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        for(Respuestas respuesta : pregunta.getRespuestas()){
            CheckBox newCheck = new CheckBox(getApplicationContext());

            newCheck.setLayoutParams(params);
            newCheck.setText(respuesta.getRespuesta());

            newCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newCheck.isChecked()){
                        _selectedCheckboxs.add(newCheck.getText().toString());
                    }else{
                        _selectedCheckboxs.remove(newCheck.getText().toString());
                    }
                }
            });

            lnRender.addView(newCheck);
        }

        Button validar = new Button(getApplicationContext());
        validar.setLayoutParams(params);
        validar.setText("Validar Respuestas");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int puntaje = 0;
                String respuestas = "";
                for (String res : _selectedCheckboxs){
                    respuestas = respuestas + "," + res;
                    for (Respuestas resp : pregunta.getRespuestas()){
                        if (resp.getRespuesta().equals(res) && resp.getCorrecta().equals("1")){
                            puntaje = puntaje + Integer.parseInt(resp.getPuntaje());
                        }
                    }
                }

                GuardarRespuesta(pregunta,respuestas,puntaje);

                lnRender.removeAllViews();
                RenderPregunta(pregunta);
            }
        });

        lnRender.addView(validar);
    }

    private void GuardarRespuesta(Preguntas pregunta, String respuestas, int puntaje){
        try {
            Partida respuesta = new Partida();
            respuesta.setPartida(_numPartida);
            respuesta.setJugador(_jugador);
            respuesta.setJuego(_juego);
            respuesta.setNivel(pregunta.getNivel());
            respuesta.setPregunta(pregunta.getPregunta());
            respuesta.setRespuestas(respuestas);
            respuesta.setPuntaje(puntaje);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat tf = new SimpleDateFormat("hh:mm");
            respuesta.setFecha(df.format(new Date()));
            respuesta.setHora(tf.format(new Date()));

            int d = 1;
            _selectedCheckboxs.clear();
            _db.InsentarRespuestaPartida(respuesta,_numPartida);
        }catch (Exception e){
            int x = 1;
        }
    }
}