package domain.Seguridad.Reglas;

import Servicios.Archivos.SistemaDeArchivos;

import java.io.IOException;

public class NoEstaEnArchivo implements ReglaContrasenia{
    private String ruta;

    public NoEstaEnArchivo(String ruta) {
        this.ruta = ruta;
    }

    public boolean cumpleRegla(String contrasenia)  {
        SistemaDeArchivos verificador = new SistemaDeArchivos();
        try {
            return !verificador.estaEnArchivo(contrasenia, ruta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
