package domain.Entidades;

import java.util.ArrayList;

public class EntidadDeTransporte extends Entidad{
    private TipoTransporte tipoTransporte;
    public EntidadDeTransporte(String nombre, String tipoEntidad, String tipoTransporte){
        this.nombre = nombre;
        this.tipoEntidad = TipoEntidad.valueOf(tipoEntidad);
        this.tipoTransporte = TipoTransporte.valueOf(tipoTransporte);
        this.establecimientos = new ArrayList<>();
    }
}
