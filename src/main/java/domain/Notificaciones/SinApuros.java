package domain.Notificaciones;

import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.ReporteDeIncidente;
import java.util.ArrayList;
import java.util.List;

public class SinApuros extends FormaDeNotificar{
  private List<ReporteDeIncidente> resumenDeIncidentes;

  public SinApuros(MedioDeComunicacion medioDeComunicacion) {
    super(medioDeComunicacion);
    this.resumenDeIncidentes = new ArrayList<>();
  }
  @Override
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente) {
    if (reporteDeIncidente.getEstado().equals(EstadoIncidente.ABIERTO)) {
      this.resumenDeIncidentes.removeIf(reporte -> reporte.equals(reporteDeIncidente));
      this.resumenDeIncidentes.add(reporteDeIncidente);
    }
    else {
      this.resumenDeIncidentes.removeIf(reporte -> reporte.equals(reporteDeIncidente));
    }
  }
  public void envioProgramado() {
    this.resumenDeIncidentes.forEach(reporte -> this.recibirNotificacion(reporte));
    this.resumenDeIncidentes.clear(); //Así se borran antes que cumplan 24hs
  }
}
