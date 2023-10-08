package models.services.APIs.Servicio2;

import lombok.Getter;
import lombok.Setter;
import models.services.APIs.Servicio2.clases.S2Incidente;
import models.services.APIs.Servicio2.clases.S2Usuario;

import java.util.List;

@Setter
@Getter
public class S2JsonRequestUsuario {
  private S2Usuario usuario;
  private List<S2Incidente> incidentes;
}
