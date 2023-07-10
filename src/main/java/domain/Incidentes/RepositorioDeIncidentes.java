package domain.Incidentes;


import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class RepositorioDeIncidentes {
  private List<ReporteDeIncidente> incidentes;

  public RepositorioDeIncidentes() {
    incidentes = new ArrayList<>();
  }

  public void registrarIncidente(ReporteDeIncidente ... incidentes) {
    Collections.addAll(this.incidentes, incidentes);
  }

  public List<ReporteDeIncidente> getIncidentesEstaSemana(){
    return incidentes.stream().filter(incidente -> incidente.dentroDeEstaSemana()).toList();
  }

}
