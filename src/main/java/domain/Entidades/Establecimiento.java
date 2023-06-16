package domain.Entidades;

import domain.Servicios.Servicio;
import lombok.Getter;
import services.Localizacion.Municipio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Establecimiento {
  private String nombre;
  private TipoEstablecimiento tipoEstablecimiento;
  private List<Servicio> servicios;
  private Municipio localizacion;

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
  @Override
  public boolean equals(Object obj) {
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
