package domain.Notificaciones.Reglas;

import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.ReporteDeIncidente;

public class IncidenteAbierto implements ReglaSinApuros {
  @Override
  public boolean cumpleRegla(ReporteDeIncidente reporteDeIncidente) {
    return reporteDeIncidente.getEstado().equals(EstadoIncidente.ABIERTO);
  }
}
