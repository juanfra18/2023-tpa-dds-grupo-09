package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

import java.util.Optional;

public class ReceptorDeNotificaciones {
  private FormaDeNotificar formaDeNotificar;
  private MedioDeComunicacion medioDeComunicacion;
  private String mail;
  private String telefono;

  public ReceptorDeNotificaciones(String mail, String telefono) {
    this.mail = mail;
    this.telefono = telefono;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.formaDeNotificar.recibirNotificacion(reporteDeIncidente);
  }
  public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente, "Solicitud de Revisión de Incidente");
  }
  public void cambiarFormaDeNotificar(String forma) {
    if (this.medioDeComunicacion == null){
      throw new RuntimeException("Antes de configurar la forma de notificar se debe configurar el medio");
    }
    else {
      switch(forma){
        case "CUANDO_SUCEDEN": this.formaDeNotificar = new CuandoSuceden(this.medioDeComunicacion); break;
        case "SIN_APUROS": this.formaDeNotificar = new SinApuros(this.medioDeComunicacion); break;
        default: throw new RuntimeException("Forma de notificar no válida");
      }
    }
  }
  public void cambiarMedioDeComunicacion(String medio) {
    switch(medio){
      case "WhatsApp": this.medioDeComunicacion = new ViaWPP(this.telefono); break;
      case "Mail": this.medioDeComunicacion = new ViaMail(this.mail); break;
      default: throw new RuntimeException("Medio de comunicación no válido");
    }
  }
}
