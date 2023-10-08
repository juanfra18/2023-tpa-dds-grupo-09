package models.services.APIs.Servicio2;

import lombok.Getter;
import lombok.Setter;
import models.services.APIs.Servicio2.clases.S2Comunidad;
import models.services.APIs.Servicio2.clases.S2Incidente;

import java.util.List;

@Setter
@Getter
public class S2JsonRequestComunidad {
  private S2Comunidad comunidad;
  private List<S2Incidente> incidentes;
}
