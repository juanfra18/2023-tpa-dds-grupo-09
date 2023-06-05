package domain.Entidades;

import java.util.ArrayList;

public class EntidadDeEstablecimiento extends Entidad{
    public EntidadDeEstablecimiento(String nombre, String tipoEntidad){
        this.nombre = nombre;
        this.tipoEntidad = TipoEntidad.valueOf(tipoEntidad);
        this.establecimientos = new ArrayList<>();
    }
}
