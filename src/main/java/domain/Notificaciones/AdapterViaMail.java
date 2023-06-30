package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public interface AdapterViaMail {
  void recibirNotificacion(ReporteDeIncidente reporteDeIncidente);
}
