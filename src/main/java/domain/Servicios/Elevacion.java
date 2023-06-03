package domain.Servicios;

public class Elevacion extends Servicio{
    private TipoElevacion tipoElevacion;
    private boolean activo;

    public Elevacion() {
    }

    public boolean estaActivo(){
        return activo;
    }
}
