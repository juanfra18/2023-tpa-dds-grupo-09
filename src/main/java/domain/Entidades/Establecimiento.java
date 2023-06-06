package domain.Entidades;

import domain.Servicios.Servicio;
import lombok.Getter;
import services.Localizacion.Localizacion;
import services.Localizacion.Localizador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Establecimiento {
  private String nombre;
  private String direccion;
  private TipoEstablecimiento tipoEstablecimiento;
  private List<Servicio> servicios;
  private Localizacion localizacion;

  public Establecimiento(String nombre, String tipoEstablecimiento, int idLocalizacion) throws IOException {
    this.nombre = nombre;
    this.tipoEstablecimiento = TipoEstablecimiento.valueOf(tipoEstablecimiento);
    this.servicios = new ArrayList<>();
    this.localizacion = Localizador.devolverLocalizacion(idLocalizacion);
  }
  public boolean servicioEnFuncionamiento(Servicio servicio) {
    if (servicios.contains(servicio)) {
      return servicio.estaActivo();
    }
    return false;
  }
  public void agregarServicio(Servicio servicio) {
    this.servicios.add(servicio);
  }
}