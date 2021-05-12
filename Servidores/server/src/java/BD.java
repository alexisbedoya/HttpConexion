/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alexi
 */
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BD {
        String driver, url, login, password;
    Connection conexion = null;

    public BD() {

        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/parqueadero";
        login = "root";
        password = "";
        try {
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, login, password);
            System.out.println("Conexion con Base de datos Ok....");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException exc) {
            System.out.println("Error al tratar de abrir la base de datos");
            System.out.println(exc.getMessage());
        }

    }

    
    // guardar registros en BD
    public void registrarEntrada(String nombre, String cedula, String placa, String estado) {
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy hh:mm:");
         String fecha = sdf.format(new Date());
         System.out.println(fecha);
        String ComandoSQL = "INSERT INTO `listado` (`nombre`, `cedula`, `placa`, `estado`) VALUES ('"+nombre+"','"+cedula+ "','"+placa+"','"+estado+"')";
        try {
            Statement stmt = conexion.createStatement();
            stmt.executeUpdate(ComandoSQL);
            System.out.println("Registro agregado!");
            stmt.close();
        } catch (java.sql.SQLException er) {
            System.out.println("No se pudo realizar la operación."+er.getMessage());
        }
    }
    public void registrarSalida(String id) {
         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy hh:mm:");
         String fecha = sdf.format(new Date());
         System.out.println(fecha);
        String ComandoSQL = "UPDATE `listado` SET `estado`=0  WHERE id="+id;
        try {

            Statement stmt = conexion.createStatement();
            stmt.executeUpdate(ComandoSQL);
            System.out.println("Registro agregado!");

            stmt.close();

        } catch (java.sql.SQLException er) {
            System.out.println("No se pudo realizar la operación.");
        }
    }

    // traer lista de registros desde BD
    
    
    public ArrayList<Registro> ListarRegistros() {
        String nombre,cedula,placa,estado,id,fechaE,fechaS;
        float temp;
        String ComandoSQL = "SELECT * FROM listado";
        ArrayList<Registro> cts = new ArrayList<>();
        Gson js = new Gson();

        try {
            Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(ComandoSQL);

            while (resultado.next()) {
                nombre = resultado.getString(1);
                cedula = resultado.getString(2);
                placa = resultado.getString(3);
                estado = resultado.getString(4);
                id =  resultado.getString(5);
                fechaE = resultado.getString(6);
                fechaS = resultado.getString(6);
                
                cts.add(new Registro(nombre, cedula, placa,estado,id,fechaE,fechaS));
            }
            stmt.close();
        } catch (java.sql.SQLException er) {
            System.out.println("No se pudo realizar la operación.");
        }
        return cts;
    }
    
}
