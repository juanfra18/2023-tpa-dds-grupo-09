package domain.Seguridad.Reglas;

import Config.Config;
import domain.Seguridad.SistemaDeArchivos;

import java.io.IOException;

public class ContieneSimbolos implements ReglaContrasenia{
    public boolean cumpleRegla(String contrasenia){
        SistemaDeArchivos verificador = new SistemaDeArchivos();
        //return contrasenia.chars().anyMatch(car -> verificador.estaEnArchivo(Character.toString(car), Config.archivoSimbolosRuta));
        return true;


        //boolean contieneSimbolo = str.matches(".[!@#$%^&()\[\]{};:'"<>?,./_\-+=].*");
    }
}
