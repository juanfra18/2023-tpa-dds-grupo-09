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
    if(forma.equals("CUANDO_SUCEDEN")) {
      this.formaDeNotificar = new CuandoSuceden(medioDeComunicacion);
    }
    else if(forma.equals("SIN_APUROS")) {
      this.formaDeNotificar = new SinApuros(medioDeComunicacion);
    }
    else {
      throw new RuntimeException("Forma de notificar no válida");
    }
  }
  public void cambiarMedioDeComunicacion(String medio) {
    if(medio.equals("WhatsApp")){
      this.medioDeComunicacion = new ViaWPP(this.telefono);
    }
    else if(medio.equals("Mail")){
      this.medioDeComunicacion = new ViaMail(this.mail);
    }
    else {
      throw new RuntimeException("Medio de comunicación no válido");
    }
  }
}
