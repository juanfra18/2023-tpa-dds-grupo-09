package domain.Entidades;

import domain.Servicios.Servicio;
import lombok.Getter;
import services.Localizacion.Municipio;

import java.util.ArrayList;
import java.util.List;

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
  public boolean establecimientoContieneServicio(Servicio servicio) {
    return servicios.stream().anyMatch(servicio1 -> servicio1.getTipo().equals(servicio.getTipo()));
  }

  @Override
  public boolean equals(Object otroEstablecimiento) {
    Establecimiento e = (Establecimiento) otroEstablecimiento;
    return e.getNombre().equals(this.nombre);
  }
}