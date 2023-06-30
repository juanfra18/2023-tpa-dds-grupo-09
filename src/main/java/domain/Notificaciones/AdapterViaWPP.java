package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public interface AdapterViaWPP {
  void recibirNotificacion(ReporteDeIncidente reporteDeIncidente);
}
