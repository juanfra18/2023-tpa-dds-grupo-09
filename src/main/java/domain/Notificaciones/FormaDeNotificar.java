package domain.Notificaciones;

import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;

public abstract class FormaDeNotificar {
  protected MedioDeComunicacion medioDeComunicacion;

  protected FormaDeNotificar(MedioDeComunicacion medioDeComunicacion) {
    this.medioDeComunicacion = medioDeComunicacion;
  }

  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente.mensaje(), "Reporte de Incidente");
  }
}
