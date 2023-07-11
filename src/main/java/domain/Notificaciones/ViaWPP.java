package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public class ViaWPP implements MedioDeComunicacion{
  private AdapterViaWPP servicioWPP;
  private String destinatario;
  public ViaWPP(String destinatario){
    this.servicioWPP = new ViaWPPConcreto(); //acoplamiento
    this.destinatario = destinatario;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, String asunto) {
    this.servicioWPP.recibirNotificacion(reporteDeIncidente, this.destinatario, asunto);
  }
}
