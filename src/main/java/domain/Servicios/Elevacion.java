package domain.Servicios;


import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "elevacion")
public class Elevacion extends Servicio{
    @Id
    @GeneratedValue
    private int id;
    @Enumerated(EnumType.STRING)
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
