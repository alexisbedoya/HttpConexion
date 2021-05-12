<?php
$bd;
function conectar(){
    try{
        global $bd;
        $bd = new mysqli("localhost","root","","parqueadero");
        
    } catch (Exceptio $error){
        $msg = array("mensaje" => $error->getMessage());
        throw new Exception();
    }
}

function consultar($sql){
    global $bd;
    $res = NULL;
    try{
        if($bd==NULL){
            conectar();
        }
        return $bd->query($sql);
    }catch(Exception $error){
        $msg = array("mensaje" => $error->getMessage());
        throw new Exception($msg);    
    }
    
}

