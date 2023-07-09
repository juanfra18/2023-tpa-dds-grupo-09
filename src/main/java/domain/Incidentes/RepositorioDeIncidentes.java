package domain.Incidentes;


import lombok.Getter;
import retrofit2.http.GET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class RepositorioDeIncidentes {
  private List<ReporteDeIncidente> reportes;

  public RepositorioDeIncidentes() {
    reportes = new ArrayList<>();
  }

  public void registrarIncidente(ReporteDeIncidente incidente) {
    //TODO
  }

}
