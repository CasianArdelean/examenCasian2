package com.example.dm2.casianExamenProm;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Actividad3 extends AppCompatActivity {

    Spinner spinner;
    SoundPool soundPool;
    int burro, caballos, cabra ,gallina,gallo,gato,ovejas,vaca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);

        spinner = (Spinner) findViewById(R.id.spinnerAudio);
        ArrayList<String> audios = new ArrayList<String>();
        try{
            InputStream is = getResources().openRawResource(R.raw.sonidos);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea = br.readLine();
            audios.add("Animales de Granja");
            while (linea != null){
                audios.add(linea);
                linea = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String[] audioArray = new String[audios.size()];
        for(int i = 0;i<audios.size();i++){
            audioArray[i] = audios.get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,audioArray);
        spinner.setAdapter(adapter);


        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        burro = soundPool.load(this,R.raw.burro,0);
        caballos = soundPool.load(this,R.raw.caballos,0);
        cabra = soundPool.load(this,R.raw.cabra,0);
        gallina = soundPool.load(this,R.raw.gallina,0);
        gallo = soundPool.load(this,R.raw.gallo,0);
        gato = soundPool.load(this,R.raw.gato,0);
        ovejas = soundPool.load(this,R.raw.ovejas,0);
        vaca = soundPool.load(this,R.raw.vaca,0);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (l==1)
                    soundPool.play(gallina,1,1,0,0,1);
                else if (l==2)
                    soundPool.play(gallo,1,1,0,0,1);
                else if (l==3)
                    soundPool.play(gato,1,1,0,0,1);
                else if (l==4)
                    soundPool.play(ovejas,1,1,0,0,1);
                else if (l==5)
                    soundPool.play(vaca,1,1,0,0,1);
                else if (l==6)
                    soundPool.play(burro,1,1,0,0,1);
                else if (l==7)
                    soundPool.play(caballos,1,1,0,0,1);
                else if (l==8)
                    soundPool.play(cabra,1,1,0,0,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void volver(View v){
        Intent intent = new Intent(Actividad3.this, MainActivity.class);
        startActivity(intent);
    }
}
