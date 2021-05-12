/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueaderoclienteescritorio.controlador;


import java.net.URI;

import java.net.http.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import parqueaderoclienteescritorio.ParqueaderoClienteEscritorio;
import parqueaderoclienteescritorio.datos.Registro;
/**
 *
 * @author alexi
 */
public class ConexionHttp {
   
    

    public ConexionHttp() {
    }
   public void conexion()  {
        //http://localhost/parqueadero/crud/operation.php?accion=listar
        //https://jsonplaceholder.typicode.com/albums
        //"http://localhost/parqueadero/crud/operation.php?accion=listar"
        
       
        
//               .thenApply(ParqueaderoClienteEscritorio::parse)
//               .thenAccept(System.out::println)
//              .join(); 

       
        salida("31");
        
    }
   
    

  public  List<Registro> listar()throws InterruptedException, ExecutionException, TimeoutException{
      HttpClient client = HttpClient.newHttpClient();
     HttpRequest request = HttpRequest.newBuilder().uri(URI.create
        ("http://localhost/parqueadero/crud/operation.php?accion=listar")).build();
      CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        JSONArray albums = new JSONArray(response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS));
        List<Registro> l = new ArrayList<Registro>();
        for (int i = 0; i < albums.length(); i++) {
            JSONObject album = albums.getJSONObject(i);
            l.add(new Registro(album.getString("nombre"),album.getString("cedula"),
                    album.getString("placa"),album.getString("estado"),
                    album.getString("id"),album.getString("horaEntrada"),
                    album.get("horaSalida").toString()));
        }
    return l;
    } 
  public void salida(String id){
   HttpClient client = HttpClient.newHttpClient();
   HttpRequest request = HttpRequest.newBuilder().uri(URI.create
        ("http://localhost/parqueadero/crud/operation.php?accion=salida&id="
           +id)).build();
  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
               .thenApply(HttpResponse::body).join();
 
  }
  
  public void entrada(String nombre, String cedula, String placa){
  HttpClient client = HttpClient.newHttpClient();
   HttpRequest request = HttpRequest.newBuilder().uri(URI.create
        ("http://localhost:8090/server/Server?accion=entrada&nombre="+
           nombre+"&cedula="+cedula+
           "&placa="+placa)).build();
  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
               .thenApply(HttpResponse::body).join();
  
  }
  
  
}
