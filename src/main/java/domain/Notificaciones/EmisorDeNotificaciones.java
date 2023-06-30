package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.Comunidad;

public class EmisorDeNotificaciones {
  private Comunidad comunidad;
  public EmisorDeNotificaciones(Comunidad comunidad) {
    this.comunidad = comunidad;
  }
  public void EnviarNotificaciones(ReporteDeIncidente reporteDeIncidente) {
    this.comunidad.getMiembros().forEach(miembroDeComunidad -> miembroDeComunidad.recibirNotificacion(reporteDeIncidente));
  }
}
