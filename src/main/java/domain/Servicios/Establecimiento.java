package domain.Servicios;

import lombok.Getter;
import services.Localizacion.Localizacion;

import java.util.List;

@Getter
public class Establecimiento {
  private String nombre;
  private String direccion;
  private TipoEstablecimiento tipoEstablecimiento;
  private List<Servicio> servicios;
  private Localizacion localizacion;
  public Establecimiento(String nombre, String tipoEstablecimiento){
    this.nombre = nombre;
    this.tipoEstablecimiento = TipoEstablecimiento.valueOf(tipoEstablecimiento);
  }
}
