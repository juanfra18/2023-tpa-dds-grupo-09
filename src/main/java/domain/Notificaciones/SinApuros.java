package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.Reglas.ReglaSinApuros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SinApuros implements FormaDeNotificar{
  private Date horario;
  private List<ReporteDeIncidente> resumenDeIncidentes;
  private List<ReglaSinApuros> reglaSinApuros;
  public SinApuros() {
    this.reglaSinApuros = new ArrayList<>();
    this.resumenDeIncidentes = new ArrayList<>();

    reglaSinApuros.add();
  }
  public void recibirNotificacion(MedioDeComunicacion medioDeComunicacion, ReporteDeIncidente reporteDeIncidente) {
    if (this.reglaSinApuros.stream().allMatch(regla -> regla.cumpleRegla(reporteDeIncidente))) {
      this.resumenDeIncidentes.removeIf(reporte -> reporte.equals(reporteDeIncidente));
      this.resumenDeIncidentes.add(reporteDeIncidente);
    }
  }
  public void envioProgramado(Date horario) {
    //TODO
    this.resumenDeIncidentes.clear(); //Así se borran antes que cumplan 24hs
  }
}
