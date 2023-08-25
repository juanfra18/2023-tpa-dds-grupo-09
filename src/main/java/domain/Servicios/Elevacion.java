package domain.Servicios;


import java.util.Objects;

public class Elevacion extends Servicio{
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
    public boolean igualito(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Elevacion otro = (Elevacion) obj;
        return tipoElevacion == otro.tipoElevacion;
    }
    @Override
    public int hashCode() {
        return Objects.hash(tipoElevacion);
    }
}
/*
public boolean equals(Object otroServicio) {
        if (otroServicio instanceof Elevacion) {
            return ((Elevacion) otroServicio).tipoElevacion == this.tipoElevacion;
        }
        else {
            return false;
        }
    }
 */