package domain.Notificaciones.Reglas;

import domain.Incidentes.ReporteDeIncidente;

public interface ReglaSinApuros {
  boolean cumpleRegla(ReporteDeIncidente reporteDeIncidente);
}
