package domain.Servicios;

import lombok.Getter;

import java.util.List;

@Getter
public class Establecimiento {
  private String nombre;
  private String direccion;
  private TipoEstablecimiento tipoEstablecimiento;
  private List<Servicio> servicios;
  public Establecimiento(String nombre, String direccion, String tipoEstablecimiento){
    this.nombre = nombre;
    this.direccion = direccion;
    this.tipoEstablecimiento = TipoEstablecimiento.valueOf(tipoEstablecimiento);
  }
}
