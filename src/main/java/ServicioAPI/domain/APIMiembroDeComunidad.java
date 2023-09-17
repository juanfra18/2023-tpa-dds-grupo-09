package ServicioAPI.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class APIMiembroDeComunidad extends APIGenerica{
  private List<APIServicio> serviciosQueAfectan;
  private List<APIEstablecimiento> establecimientosDeInteres;

  public boolean afectadoPor(APIIncidente incidente) {
    return this.serviciosQueAfectan.stream().anyMatch(s -> s.getId() == incidente.getServicio().getId())
        &&
        this.establecimientosDeInteres.stream().anyMatch(e -> e.getId() == incidente.getEstablecimiento().getId());
  }
}
