package domain.Seguridad.Reglas;

import services.Archivos.SistemaDeArchivos;
import domain.Seguridad.NoSeEncontroElArchivo;

import java.io.IOException;

public class NoEstaEnArchivo implements ReglaContrasenia{
    private String ruta;

    public NoEstaEnArchivo(String ruta) {
        this.ruta = ruta;
    }

    public boolean cumpleRegla(String contrasenia)  {
        try {
            return !SistemaDeArchivos.estaEnArchivo(contrasenia, ruta);
        } catch (IOException e) {
            throw new NoSeEncontroElArchivo("No se encontro el archivo");
        }
    }
}
