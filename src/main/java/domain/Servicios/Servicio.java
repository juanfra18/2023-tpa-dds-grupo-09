package domain.Servicios;

import domain.Entidades.Establecimiento;
import domain.Persistencia.Persistente;

import javax.persistence.MappedSuperclass;
import javax.persistence.Persistence;
import java.util.Objects;
@MappedSuperclass
public abstract class Servicio extends Persistente {
  public abstract boolean estaActivo();
  public abstract String getTipo();

  public boolean igualito(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Servicio otro = (Servicio) obj;
    return Objects.equals(getTipo(),otro.getTipo())
        && Objects.equals(getClass(),otro.getClass());
  }

}

