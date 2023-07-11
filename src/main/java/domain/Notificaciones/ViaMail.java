package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public class ViaMail implements MedioDeComunicacion{
  private AdapterViaMail servicioMail;
  private String destinatario;
  public ViaMail(String destinatario){
    this.servicioMail = new ViaMailJavax(); //acoplamiento con biblioteca externa
    this.destinatario = destinatario;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, String asunto) {
    this.servicioMail.recibirNotificacion(reporteDeIncidente, this.destinatario, asunto);
  }
}