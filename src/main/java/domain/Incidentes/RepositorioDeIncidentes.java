package domain.Incidentes;


import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public class RepositorioDeIncidentes { //TODO
  private List<Incidente> incidentes;

  public RepositorioDeIncidentes() {
    incidentes = new ArrayList<>();
  }

  public void registrarIncidente(ReporteDeIncidente reporteDeIncidente) {
    List<Incidente> incidentesSobreLaMismaProblematica = this.incidentes.stream().filter(i -> i.getEstablecimiento().igualito(reporteDeIncidente.getEstablecimiento()) && i.getServicio().igualito(reporteDeIncidente.getServicio())).toList();
    Incidente mismoIncidente = null;

    if(!incidentesSobreLaMismaProblematica.isEmpty())
    {
       mismoIncidente  = incidentesSobreLaMismaProblematica.get(incidentesSobreLaMismaProblematica.size()-1);
    }
    //para obtener el mismo incidente pero el mas reciente

    if (mismoIncidente != null && !mismoIncidente.cerrado()) {
      if (reporteDeIncidente.esDeCierre()) { //hay q cerrar el reporte y ver si no hay otros tmb sin cerrar
        mismoIncidente.setReporteDeCierre(reporteDeIncidente);
      }
      else
      {
        mismoIncidente.agregarReporteDeApertura(reporteDeIncidente);
      }
    }
    else
    {
      if(!reporteDeIncidente.esDeCierre()) {
        Incidente incidente = new Incidente(reporteDeIncidente.getEstablecimiento(), reporteDeIncidente.getServicio());
        incidente.agregarReporteDeApertura(reporteDeIncidente);
        this.incidentes.add(incidente);
      }
    }
  }

  public List<Incidente> getIncidentesEstaSemana(){
    return incidentes.stream().filter(incidente -> incidente.primeraApertura().dentroDeEstaSemana()).toList();
  }

}
