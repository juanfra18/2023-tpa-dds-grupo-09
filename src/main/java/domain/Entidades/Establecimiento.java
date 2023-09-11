package domain.Entidades;

import domain.Incidentes.Posicion;
import domain.Persistencia.Persistente;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;
import services.Localizacion.Municipio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name="establecimiento")
public class Establecimiento extends Persistente {
  @Column(name = "nombre")
  @Setter
  private String nombre;
  @Enumerated(EnumType.STRING)
  @Setter
  private TipoEstablecimiento tipoEstablecimiento;
  @ManyToMany //todo para que la tabla intermedia tenga un id y permite varias entradas iguales
  private List<Servicio> servicios;
  @ManyToOne
  @JoinColumn(name="municipio_id",referencedColumnName = "id")
  @Setter
  private Municipio localizacion;
  @ManyToOne
  @JoinColumn(name = "posicion_id", referencedColumnName = "id")
  private Posicion posicion;

  public Establecimiento() {
    this.servicios = new ArrayList<>();
  }
  public boolean estaEnFuncionamiento(Servicio servicio) { // X CANTIDAD DE TIEMPO
    if (servicios.contains(servicio)) {
      return servicio.estaActivo();
    }
    return false;
  }
  public void agregarServicio(Servicio servicio) {
    this.servicios.add(servicio);
  }
  public boolean igualito(Establecimiento establecimiento) {
    if (this == establecimiento) {
      return true;
    }
    if (establecimiento == null || getClass() != establecimiento.getClass()) {
      return false;
    }
    Establecimiento otro = establecimiento;
    return Objects.equals(nombre, otro.nombre)
        && Objects.equals(tipoEstablecimiento,otro.tipoEstablecimiento)
        && Objects.equals(localizacion.getId(),otro.localizacion.getId());
  }


}
