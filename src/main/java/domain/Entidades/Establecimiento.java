package domain.Entidades;

import domain.Personas.InteresadoEnServicios;
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
  public boolean estaEnFuncionamiento(Servicio servicio) { // X CANTIDAD DE TIEMPO
    if (servicios.contains(servicio)) {
      return servicio.estaActivo();
    }
    return false;
  }
  public boolean establecimientoContieneServicios(Establecimiento establecimiento, List<Servicio> servicios) {
    return servicios.stream().anyMatch(servicio -> establecimiento.getServicios().contains(servicio));
  }














/*
public void revisionTemporalDeServicios() {
    if (!estaEnFuncionamiento(Servicio servicio))
      if(estaInteresado (InteresadoEnServicios persona, Servicio servicio)){
      enviarNotificacion(InteresadoEnServicios);
    }
  }

  public void enviarNotificaciones(InteresadoEnServicios persona) {
    mensaje = "Flaco no funciona el servicio"
    persona.recibirMensaje(mensaje);
 */
}