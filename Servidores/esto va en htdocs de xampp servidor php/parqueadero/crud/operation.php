
<?php

require_once '../bd/conexion.php';
$accion = @$_REQUEST["accion"];
switch ($accion) {

    case "entrada":
        registrar_e();
        break;
     case "salida":
        registrar_s();
        break;
    case "listar":
        
        listar();
        break;

    default:
        break;
}
/*
function login() {
    $email = @$_REQUEST["email"];
    $pass = @$_REQUEST["psw"];
    try {
        $res = consultar("SELECT * FROM Usuarios WHERE email ='$email' AND password='$pass'");
        if ($res != NULL && $res->num_rows > 0) {
            $json = json_encode($res->fetch_assoc());
            echo $json;
             //liberar el conjunto de resultados 
            $res->free();
        } else {
            echo json_encode(array("mensaje" => "Acceso denegado"));
        }
    } catch (Exception $error) {
        echo json_encode(array("mensaje" => $error->getTraceAsString()));
    }
}
*/

function registrar_e() {
    $nombre = @$_REQUEST["nombre"];
    $cedula = @$_REQUEST["cedula"];
    $placa = @$_REQUEST["placa"];
    $estado = @$_REQUEST["estado"];
    try {
        $res = consultar("INSERT INTO `listado` (`nombre`, `cedula`, `placa`, `estado`) VALUES('$nombre','$cedula','$placa','$estado')");
        echo json_encode(array("mensaje" => "OK"));
      
    } catch (Exception $error) {
        echo json_encode(array("mensaje" => "Usuario No Existe"));
    }
}
function registrar_s(){
    $estado = "0";
    $id = @$_REQUEST["id"];
    try {
        $res = consultar("UPDATE `listado` SET `estado`='$estado'  WHERE id='$id'");
        echo json_encode(array("mensaje" => "OK"));
       
    } catch (Exception $error) {
        echo json_encode(array("mensaje" => "Usuario No Existe"));
    }
    
}

function listar() {
    try {
        $res = consultar("SELECT * FROM `listado`");
        if ($res != NULL && $res->num_rows > 0) {
            $json = json_encode($res->fetch_all(1));
            echo $json;
            /* liberar el conjunto de resultados */
            $res->free();
        } else {
            echo json_encode(array("mensaje" => "No hay Usuarios"));
        }
    } catch (Exception $error) {
        echo json_encode(array("mensaje" => $error->getTraceAsString()));
    }
}
