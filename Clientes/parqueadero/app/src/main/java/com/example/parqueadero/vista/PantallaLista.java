package com.example.parqueadero.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parqueadero.R;
import com.example.parqueadero.controlador.ConexionHttpPostServer;
import com.example.parqueadero.datos.Registro;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.httpclient.NameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PantallaLista extends AppCompatActivity {
    private ListView listaRegistro;
    private ConexionHttpPostServer conexionServidor;
    private Button btnSalida, btnAtras;
    private ProgressDialog barraProgreso;
    private String ids;
    List<Registro> listaR;
    ArrayAdapter<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista);
        listaRegistro = (ListView) findViewById(R.id.listaUsuarios);
        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnSalida = (Button) findViewById(R.id.btnSalida);
        items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        conexionServidor = new ConexionHttpPostServer();
        conexionServidor.direccionDelServidor = "http://192.168.1.2/parqueadero/crud/operation.php";
        new TareaListarTodo().execute("", "");
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(PantallaLista.this,Agregar.class);

                startActivity(intento);
            }
        });
        listaRegistro.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ids = listaR.get(position).getId();

            }
        });
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });

    }

    private void modificar(){
        TareaGuardar tarea = new TareaGuardar();
        tarea.execute("");
    }
    public String guardarUsuario() {
        String url = "http://192.168.1.2/parqueadero/crud/operation.php";
        ConexionHttpPostServer.direccionDelServidor = url;
        NameValuePair parametroAccion;
        parametroAccion = new NameValuePair("accion", "salida");
        NameValuePair parametroEmail = new NameValuePair("id", ids);

        List<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(parametroAccion);
        parametros.add(parametroEmail);
        ConexionHttpPostServer conexionHttp = new ConexionHttpPostServer();
        try {
            String jsonRespuesta = conexionHttp.conexionConElServidor(parametros, url);
            if (jsonRespuesta != null) {
                Gson json = new Gson();
                Mensaje m = json.fromJson(jsonRespuesta, Mensaje.class);
                return m.getMensaje();
            }else{
                return "ERROR";
            }} catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }
    class TareaGuardar extends AsyncTask<String, String, String> {
        ProgressDialog barraDeprogreso;
        String codigo, password, nombre, accion;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barraDeprogreso = new ProgressDialog(PantallaLista.this);
            barraDeprogreso.setMessage("Conectando...");
            barraDeprogreso.setIndeterminate(false);
            barraDeprogreso.setCancelable(false);
            barraDeprogreso.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String respuesta = guardarUsuario();
            return respuesta;
        }
        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.equalsIgnoreCase("OK") == true) {
                Toast.makeText(PantallaLista.this, "Usuario Guardado con Exito",

                        Toast.LENGTH_LONG).show();

                try {
                    Thread.sleep(2000);
                    barraDeprogreso.dismiss();
                    Intent intento = new Intent(PantallaLista.this,

                            Agregar.class);

                    startActivity(intento);
                } catch (InterruptedException e) {
                    barraDeprogreso.dismiss();
                }
            } else {
                barraDeprogreso.dismiss();
                Toast.makeText(PantallaLista.this, "ERROR: Respuesta Incorrecta",

                        Toast.LENGTH_LONG).show();

            }
        }}
    class Mensaje {
        String mensaje;
        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
        public String getMensaje() {
            return mensaje;
        }
    }

    class TareaListarTodo extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            barraProgreso = new ProgressDialog(PantallaLista.this);
            barraProgreso.setMessage("Conectando...");
            barraProgreso.setIndeterminate(false);
            barraProgreso.setCancelable(false);
            barraProgreso.show();
        }
        @Override
        protected String doInBackground(String... params) {
            boolean resultado = procesarRespuestaPeticion();
            if (resultado == true) {
                return "OK";
            } else {
                return "NO";
            }
        }
        @Override
        protected void onPostExecute(String resultado) {
            barraProgreso.dismiss();
            if (resultado.equals("OK")) {
                mostrarUsuariosEnLista();
            } else {
                Toast.makeText(PantallaLista.this, "Error en la Tarea",

                        Toast.LENGTH_LONG).show();

            }
        }
    }
    private void mostrarUsuariosEnLista() {
        int i = 1;
// for (Map.Entry<String, Usuario> alguien : listaUsuarios.entrySet() ) {
        for (Registro alguien : listaR) {
            i++;
            System.out.println("No. " + i + " " + alguien);
            String elemento = alguien.getId()+"-  "+alguien.getNombre() + " - " + alguien.getPlaca();
            items.add(elemento);
        }
        listaRegistro.setAdapter(items);
    }
    private boolean procesarRespuestaPeticion() {
        ArrayList<NameValuePair> listaParametros = new ArrayList<NameValuePair>();
        NameValuePair parametro = new NameValuePair("accion", "listar");
        listaParametros.add(parametro);
        try {
            String resultadoDelServidor = conexionServidor.conexionConElServidor(listaParametros, conexionServidor.direccionDelServidor);

            if (resultadoDelServidor != null && resultadoDelServidor.length() > 0) {
                System.out.println("JSON: " + resultadoDelServidor);
                Gson json = new Gson();
//Type lista = new TypeToken<Map<String,Usuario>>(){}.getType();
                Type lista = new TypeToken<List<Registro>>() {
                }.getType();
                listaR = json.fromJson(resultadoDelServidor, lista);
                return true;
            } else {
                return false;
            }
        } catch (Exception error) {
            error.printStackTrace();
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}