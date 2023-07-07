package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public abstract class FormaDeNotificar {
  protected MedioDeComunicacion medioDeComunicacion;
  protected String destinatario;

  public FormaDeNotificar(MedioDeComunicacion medioDeComunicacion, String destinatario) {
    this.medioDeComunicacion = medioDeComunicacion;
    this.destinatario = destinatario;
  }

  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente, this.destinatario, "Reporte de Incidente");
  }
}
