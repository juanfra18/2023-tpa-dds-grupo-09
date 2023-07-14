package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public abstract class FormaDeNotificar {
  protected MedioDeComunicacion medioDeComunicacion;

  public FormaDeNotificar(MedioDeComunicacion medioDeComunicacion) {
    this.medioDeComunicacion = medioDeComunicacion;
  }

  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente.mensaje(), "Reporte de Incidente");
  }
}
