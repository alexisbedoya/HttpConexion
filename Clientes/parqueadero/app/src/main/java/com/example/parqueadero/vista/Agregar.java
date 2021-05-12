package com.example.parqueadero.vista;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.parqueadero.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Agregar extends AppCompatActivity {
    private EditText inputNombre,inputCedula,inputPlaca;
    private Button btnAgregar,btnListar;

    public static final String  URL = "http://192.168.1.2:8090/server/Server?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        inputNombre = (EditText) findViewById(R.id.inputNombre);
        inputCedula = (EditText) findViewById(R.id.inputCedula);
        inputPlaca = (EditText) findViewById(R.id.inputPlaca);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnListar = (Button) findViewById(R.id.btnListar);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Agregar.this,PantallaLista.class);

                startActivity(intento);
            }
        });
      btnAgregar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              guardar(inputNombre.getText().toString(),inputCedula.getText().toString(),inputPlaca.getText().toString());
          }
      });
    }
    private void guardar(String nombre,String cedula, String placa){

        if(nombre!=null&& cedula!=null&&placa!=null){
            String url = URL+"accion="+"entrada"+"&nombre="+nombre+"&cedula="+cedula+"&placa="+placa;
            GetXMLTask task = new GetXMLTask();
            task.execute(new String[] { url });
        }

    }
    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;

            java.net.URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String output) {
            Toast.makeText(Agregar.this, "Usuario Guardado con Exito", Toast.LENGTH_LONG).show();
        }
    }


}