/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueaderoclienteescritorio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import parqueaderoclienteescritorio.controlador.ConexionHttp;

/**
 *
 * @author alexi
 */
public class ParqueaderoClienteEscritorio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     ConexionHttp a = new ConexionHttp();
     a.conexion();

    }

    
}
