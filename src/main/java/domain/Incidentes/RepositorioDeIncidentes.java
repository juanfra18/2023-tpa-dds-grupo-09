package domain.Incidentes;


import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class RepositorioDeIncidentes {
  private List<Incidente> incidentes;

  public RepositorioDeIncidentes() {
    incidentes = new ArrayList<>();
  }

  public void registrarIncidente(Incidente ... incidentes) {
    Collections.addAll(this.incidentes, incidentes);
  }

  public List<Incidente> getIncidentesEstaSemana(){
    return incidentes.stream().filter(incidente -> incidente.primeraApertura().dentroDeEstaSemana()).toList();
  }

}
