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
  private TipoEstablecimiento tipoEstablecimiento;
  private List<Servicio> servicios;
  private Localizacion localizacion;

  public Establecimiento(String nombre, String tipoEstablecimiento, int idLocalizacion) throws IOException {
    this.nombre = nombre;
    this.tipoEstablecimiento = TipoEstablecimiento.valueOf(tipoEstablecimiento);
    this.servicios = new ArrayList<>();
    this.localizacion = Localizador.devolverLocalizacion(idLocalizacion);
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