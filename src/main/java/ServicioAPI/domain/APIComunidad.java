package ServicioAPI.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class APIComunidad extends APIGenerica{
  private List<APIIncidente> incidentes;
  private List<APIMiembroDeComunidad> miembros;

  public boolean incidenteEsDeComunidad(APIIncidente incidente) {
    return incidentes.stream().anyMatch(i -> i.getId() == incidente.getId()); //TODO ver si con contains basta
  }
}
