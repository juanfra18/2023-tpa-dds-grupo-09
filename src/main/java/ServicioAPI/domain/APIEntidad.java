package ServicioAPI.domain;

import lombok.Getter;

import java.util.List;
@Getter
public class APIEntidad extends APIGenerica{
  private List<APIEstablecimiento> establecimientos;
}
