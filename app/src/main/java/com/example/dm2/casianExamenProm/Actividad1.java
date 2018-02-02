package com.example.dm2.casianExamenProm;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Actividad1 extends AppCompatActivity {

    private String urlVitoria = "http://xml.tutiempo.net/xml/8043.xml";
    private String urlBilbao = "http://xml.tutiempo.net/xml/8050.xml";
    private String urlDonostia = "http://xml.tutiempo.net/xml/4917.xml";

    private final int PERMISION_INTERNET = 1;
    private TextView text;
    private String ciudad;
    private List<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad1);

        text = (TextView)findViewById(R.id.txt_temperaturas);
    }

    public void getBilbao(View v){
        LoadXml loader = new LoadXml();
        loader.execute(urlBilbao);
        ciudad = "BILBO-BILBAO";
    }

    public void getVitoria(View v){
        LoadXml loader = new LoadXml();
        loader.execute(urlVitoria);
        ciudad = "VITORIA-GASTEIZ";
    }

    public void getSanSebastian(View v){
        LoadXml loader = new LoadXml();
        loader.execute(urlDonostia);
        ciudad = "DONOSTIA-SAN SEBASTIAN";
    }

    public void volver(View v){
        Intent intent = new Intent(Actividad1.this, MainActivity.class);
        startActivity(intent);
    }

    class LoadXml extends AsyncTask<String,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            Xml parser = new Xml(params[0]);
            datos = parser.returnTimeTempSky();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            String h = datos.get(0);
            String t = datos.get(1);
            String cielo = datos.get(2);

            text.setText("Tiempo actual en:" + ciudad + "\n\n"
                    + "Hora : " + h + "\n"
                    + "Temperatura : " + t + "\n"
                    + "Estado del cielo : " + cielo + "\n");
        }
    }
}



