package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

public class ReceptorDeNotificaciones {
  private FormaDeNotificar formaDeNotificar;
  private MedioDeComunicacion medioDeComunicacion;
  private String mail;
  private String telefono;
  private String destinatario;

  public ReceptorDeNotificaciones(String mail, String telefono) {
    this.mail = mail;
    this.telefono = telefono;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.formaDeNotificar.recibirNotificacion(reporteDeIncidente);
  }
  public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente, this.destinatario, "Solicitud de Revisión de Incidente");
  }
  public void cambiarFormaDeNotificar(String forma) {
    //TODO
  }
  public void cambiarMedioDeComunicacion(String medio) {
    //TODO
  }
}
