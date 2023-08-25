package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public class ReceptorDeNotificaciones {
  private FormaDeNotificar formaDeNotificar;
  private MedioDeComunicacion medioDeComunicacion;
  private String mail;
  private String telefono;

  public ReceptorDeNotificaciones(MedioDeComunicacion medioDeComunicacion, FormaDeNotificar formaDeNotificar ,String mail, String telefono) {
    this.cambiarMedioDeComunicacion(medioDeComunicacion);
    this.cambiarFormaDeNotificar(formaDeNotificar);
    this.mail = mail;
    this.telefono = telefono;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.formaDeNotificar.recibirNotificacion(reporteDeIncidente);
  }
  public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente.mensaje(), "Solicitud de Revisión de Incidente");
  }
  public void cambiarFormaDeNotificar(FormaDeNotificar forma) {
    this.formaDeNotificar = forma;
  }
  public void cambiarMedioDeComunicacion(MedioDeComunicacion medio) {
    this.medioDeComunicacion = medio;
  }
}
