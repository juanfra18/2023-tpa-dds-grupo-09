package models.domain.Notificaciones;

import models.domain.Incidentes.EstadoIncidente;
import models.domain.Incidentes.ReporteDeIncidente;
import java.util.ArrayList;
import java.util.List;

public class SinApuros extends FormaDeNotificar{
  private List<ReporteDeIncidente> resumenDeIncidentes;

  public SinApuros() {
    this.resumenDeIncidentes = new ArrayList<>();
  }
  @Override
  public void recibirNotificacion(MedioDeComunicacion medioDeComunicacion, ReporteDeIncidente reporteDeIncidente, String destinatario) {
    if (reporteDeIncidente.getClasificacion().equals(EstadoIncidente.ABIERTO)) {
      this.resumenDeIncidentes.removeIf(reporte -> reporte.igualito(reporteDeIncidente));
      this.resumenDeIncidentes.add(reporteDeIncidente);
    }
    else {
      this.resumenDeIncidentes.removeIf(reporte -> reporte.igualito(reporteDeIncidente));
    }
  }
  @Override
  public void envioProgramado(MedioDeComunicacion medioDeComunicacion, String destinatario) {
    this.resumenDeIncidentes.forEach(reporte -> super.recibirNotificacion(medioDeComunicacion,reporte,destinatario));
    this.resumenDeIncidentes.clear(); //Así se borran antes que cumplan 24hs
  }
}
