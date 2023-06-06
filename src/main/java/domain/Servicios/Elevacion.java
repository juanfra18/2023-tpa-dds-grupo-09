package domain.Servicios;


public class Elevacion implements Servicio{
    private TipoElevacion tipoElevacion;

    public boolean estaActivo() {
        //TODO
        return true;
    }
    public String getTipo(){
        return String.valueOf(tipoElevacion);
    }
    public Elevacion(String tipoElevacion) {
        this.tipoElevacion = TipoElevacion.valueOf(tipoElevacion);
    }
}
