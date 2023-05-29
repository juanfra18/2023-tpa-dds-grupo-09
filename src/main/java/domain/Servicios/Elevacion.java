package domain.Servicios;

public class Elevacion implements Servicio{
    private TipoElevacion tipoElevacion;
    private boolean activo;
    @Override
    public boolean estaActivo(){
        return activo;
    }
}
