package models.domain.Servicios;

import models.persistence.Persistente;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "servicio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Servicio extends Persistente {
  public abstract boolean estaActivo();
  public abstract String getTipo();

  public String nombre(){
    return this.getClass().getSimpleName();
  }

  public String estado(){
    if(estaActivo())
      return "En funcionamiento";
    else
      return "Deshabilitado";
  }

  public abstract void setTipo(String tipo);
  public boolean igualito(Servicio servicio) {
    if (this == servicio) {
      return true;
    }
    if (servicio == null || getClass() != servicio.getClass()) {
      return false;
    }
    Servicio otro = servicio;
    return Objects.equals(getTipo(),otro.getTipo())
        && Objects.equals(getClass(),otro.getClass());
  }
}

