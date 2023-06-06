package domain.Entidades;

import java.io.IOException;

public class EntidadDeTransporte extends Entidad{
    private TipoTransporte tipoTransporte;
    public EntidadDeTransporte(String nombre, String tipoTransporte, int idLocalizacion) throws IOException {
        super(nombre, idLocalizacion);
        this.tipoTransporte = TipoTransporte.valueOf(tipoTransporte);
    }
}
