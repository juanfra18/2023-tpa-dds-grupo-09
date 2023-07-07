package domain.Notificaciones.Reglas;

import domain.Incidentes.ReporteDeIncidente;

public interface ReglaNotificacion {
  boolean cumpleRegla(ReporteDeIncidente reporteDeIncidente);
}
