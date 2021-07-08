package com.example.juegoejemplo;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.juegoejemplo.Entidades.Preguntas;
import com.example.juegoejemplo.Entidades.Respuestas;
import com.example.juegoejemplo.Services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartidaActivity extends AppCompatActivity {

    List<Preguntas> _preguntas;
    Preguntas _preguntaActual;

    LinearLayout lnRender;
    TextView nivel,tipo,pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        InicializarControles();
        ObtenerPreguntas();
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

    private void RenderPreguntaMejorOpcion(Preguntas pregunta) {

        RadioGroup group = new RadioGroup(getApplicationContext());
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        group.setLayoutParams(params);

        for(Respuestas respuesta : pregunta.getRespuestas()){
            RadioButton newCheck = new RadioButton(getApplicationContext());


            newCheck.setLayoutParams(params);
            newCheck.setText(respuesta.getRespuesta());

            group.addView(newCheck);
        }

        lnRender.addView(group);
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
                if (pregunta.getRespuestas().get(0).getRespuesta().equals("1")){
                    Toast.makeText(getApplicationContext(),"CORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"INCORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }
                lnRender.removeAllViews();
                RenderPregunta(pregunta);
            }
        });

        falso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pregunta.getRespuestas().get(0).getRespuesta().equals("0")){
                    Toast.makeText(getApplicationContext(),"CORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"INCORRECTOOOOOOO", Toast.LENGTH_LONG).show();
                }
                lnRender.removeAllViews();
                RenderPregunta(pregunta);
            }
        });

        lnRender.addView(verdadero);
        lnRender.addView(falso);

        //aqui se hace el evento click y se determina quien es la respuesta correcta.

    }

    private void RenderPreguntaOpcionMultiple(Preguntas pregunta) {
        for(Respuestas respuesta : pregunta.getRespuestas()){
            CheckBox newCheck = new CheckBox(getApplicationContext());

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            newCheck.setLayoutParams(params);
            newCheck.setText(respuesta.getRespuesta());

            lnRender.addView(newCheck);
        }
    }

}