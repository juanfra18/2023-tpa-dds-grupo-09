package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public class CuandoSuceden implements FormaDeNotificar{
  public void recibirNotificacion(MedioDeComunicacion medioDeComunicacion, ReporteDeIncidente reporteDeIncidente) {
    medioDeComunicacion.recibirNotificacion(reporteDeIncidente);
  }
}
