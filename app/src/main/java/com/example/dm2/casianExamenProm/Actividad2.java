package com.example.dm2.casianExamenProm;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Actividad2 extends AppCompatActivity {


    String url = "http://www.webservicex.net/periodictable.asmx/GetAtomicNumber";

    String simbolo, numAt, pesoAt, ebulli, densidad;
    EditText element;
    TextView simb,nuAt,pAt,ebu,den;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        element = (EditText) findViewById(R.id.txt_element);
        simb = (TextView) findViewById(R.id.simb);
        nuAt = (TextView) findViewById(R.id.numAt);
        pAt = (TextView) findViewById(R.id.pesoAt);
        ebu = (TextView) findViewById(R.id.ebu);
        den = (TextView) findViewById(R.id.dens);
    }

    public void getElementInfo(View v){
        AsyncPost task = new AsyncPost();
        task.execute(element.getText().toString());
    }

    public void volver(View v){
        Intent intent = new Intent(Actividad2.this, MainActivity.class);
        startActivity(intent);
    }

    private class AsyncPost extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection con;
            try {
                URL url = new URL(Actividad2.this.url);
                String param = "ElementName=" + URLEncoder.encode(params[0],"UTF-8");
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setFixedLengthStreamingMode(param.getBytes().length);
                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                PrintWriter out = new PrintWriter((con.getOutputStream()));
                out.print(param);
                out.close();

                String result = "";

                Scanner inStream = new Scanner(con.getInputStream());
                while (inStream.hasNextLine()){
                    result = inStream.nextLine();
                    if (result.indexOf("AtomicNumber")>0){
                        numAt = result.replace("&lt;AtomicNumber&gt;","").replace("&lt;/AtomicNumber&gt;","");
                    } else if (result.indexOf("Symbol")>0){
                        simbolo = result.replace("&lt;Symbol&gt;","").replace("&lt;/Symbol&gt;","");
                    } else if (result.indexOf("AtomicWeight")>0){
                        pesoAt = result.replace("&lt;AtomicWeight&gt;","").replace("&lt;/AtomicWeight&gt;","");
                    } else if (result.indexOf("Density")>0){
                        densidad = result.replace("&lt;Density&gt;","").replace("&lt;/Density&gt;","");
                    } else if (result.indexOf("BoilingPoint")>0) {
                        ebulli = result.replace("&lt;BoilingPoint&gt;", "").replace("&lt;/BoilingPoint&gt;", "");
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            simb.setText(Html.fromHtml(simb.getText()+"<font color=black> "+simbolo+"</font>"));
            nuAt.setText(Html.fromHtml(nuAt.getText()+" <font color=black> "+numAt+"</font>"));
            pAt.setText(Html.fromHtml(pAt.getText()+" <font color=black> "+pesoAt+"</font>"));
            ebu.setText(Html.fromHtml(ebu.getText()+" <font color=black> "+ebulli+"</font>"));
            den.setText(Html.fromHtml(den.getText()+" <font color=black> "+densidad+"</font>"));
        }
    }
}
