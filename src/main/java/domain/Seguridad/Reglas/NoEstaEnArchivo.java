package domain.Seguridad.Reglas;

import domain.Seguridad.SistemaDeArchivos;

public class NoEstaEnArchivo implements ReglaContrasenia{
    private String ruta;

    public NoEstaEnArchivo(String ruta) {
        this.ruta = ruta;
    }

    public boolean cumpleRegla(String contrasenia){
        SistemaDeArchivos verificador = new SistemaDeArchivos();
        //return !verificador.estaEnArchivo(contrasenia, ruta);
        return true;
    }
}
