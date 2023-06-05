package domain.Entidades;

import domain.Servicios.Servicio;
import lombok.Getter;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Localizacion;

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

  public Establecimiento(String nombre, String tipoEstablecimiento) {
    this.nombre = nombre;
    this.tipoEstablecimiento = TipoEstablecimiento.valueOf(tipoEstablecimiento);
    this.servicios = new ArrayList<>();
  }

  public void asignarLocalizacion(int id) throws IOException {
    ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
    ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();
    this.localizacion = listadoDeProvincias.provinciaDeId(id).get();
  }
  public boolean servicioEnFuncionamiento(Servicio servicio) {
    if (servicios.contains(servicio)) {
      return servicio.estaActivo();
    }
    return false;
  }
}