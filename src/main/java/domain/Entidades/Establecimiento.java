package domain.Entidades;

import domain.Incidentes.Posicion;
import domain.Servicios.Servicio;
import lombok.Getter;
import services.Localizacion.Municipio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name="establecimiento")
public class Establecimiento {
  @Id
  @GeneratedValue
  private int id;
  @Column
  private String nombre;
  @Enumerated(EnumType.STRING)
  private TipoEstablecimiento tipoEstablecimiento;
  @Transient //MANY TO MANY A HERENCIA
  private List<Servicio> servicios;
  @ManyToOne
  @JoinColumn(name="municipio_id",referencedColumnName = "id")
  private Municipio localizacion;
  @Transient
  private Posicion posicion;

  public Establecimiento(String nombre, String tipoEstablecimiento, Municipio municipio) {
    this.nombre = nombre;
    this.tipoEstablecimiento = TipoEstablecimiento.valueOf(tipoEstablecimiento);
    this.servicios = new ArrayList<>();
    this.localizacion = municipio;
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
  public boolean igualito(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Establecimiento otro = (Establecimiento) obj;
    return Objects.equals(nombre, otro.nombre)
        && Objects.equals(tipoEstablecimiento,otro.tipoEstablecimiento)
        && Objects.equals(localizacion.getId(),otro.localizacion.getId());
  }


}
