package domain.Notificaciones;

import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;

public abstract class FormaDeNotificar {
  public void recibirNotificacion(MedioDeComunicacion medioDeComunicacion, ReporteDeIncidente reporteDeIncidente, String destinatario){
    medioDeComunicacion.recibirNotificacion(reporteDeIncidente.mensaje(), "Reporte de Incidente", destinatario);
  }
}
