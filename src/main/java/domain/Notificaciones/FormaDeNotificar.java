package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public interface FormaDeNotificar {
  void recibirNotificacion(MedioDeComunicacion medioDeComunicacion, ReporteDeIncidente reporteDeIncidente);
}
