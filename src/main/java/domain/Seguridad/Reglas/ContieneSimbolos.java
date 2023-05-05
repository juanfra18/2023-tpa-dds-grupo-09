package domain.Seguridad.Reglas;

import Config.Config;
import domain.Seguridad.SistemaDeArchivos;

import java.io.IOException;

public class ContieneSimbolos implements ReglaContrasenia{
    public boolean cumpleRegla(String contrasenia){
        SistemaDeArchivos verificador = new SistemaDeArchivos();
        return contrasenia.chars().anyMatch(car -> {
            try {
                return verificador.estaEnArchivo(Character.toString(car), Config.ARCHIVO_SIMBOLOS_RUTA);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
