package domain.Servicios;

import domain.Entidades.TipoTransporte;

public class Elevacion implements Servicio{
    private TipoElevacion tipoElevacion;

    public boolean estaActivo() {
        //TODO
        return true;
    }
    public Elevacion(String tipoElevacion) {
        this.tipoElevacion = TipoElevacion.valueOf(tipoElevacion);
    }
}
