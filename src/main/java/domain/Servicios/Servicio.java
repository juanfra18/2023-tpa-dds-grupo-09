package domain.Servicios;

import domain.Entidades.Establecimiento;

import java.util.Objects;

public abstract class Servicio {
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

