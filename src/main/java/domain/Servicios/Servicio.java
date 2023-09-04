package domain.Servicios;

import domain.Entidades.Establecimiento;
import domain.Persistencia.Persistente;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "servicio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
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

