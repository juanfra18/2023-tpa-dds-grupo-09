package domain.Seguridad.Reglas;

import Servicios.Archivos.SistemaDeArchivos;
import domain.Seguridad.NoSeEncontroElArchivo;

import java.io.IOException;

public class NoEstaEnArchivo implements ReglaContrasenia{
    private String ruta;
    private  SistemaDeArchivos verificador;

    public NoEstaEnArchivo(String ruta, SistemaDeArchivos verificador) {
        this.ruta = ruta;
        this.verificador = verificador;
    }

    public boolean cumpleRegla(String contrasenia)  {
        try {
            return !verificador.estaEnArchivo(contrasenia, ruta);
        } catch (IOException e) {
            throw new NoSeEncontroElArchivo("No se encontro el archivo");
        }
    }
}
