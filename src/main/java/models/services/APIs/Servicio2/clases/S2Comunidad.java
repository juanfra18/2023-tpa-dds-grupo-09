package models.services.APIs.Servicio2.clases;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class S2Comunidad {
  private String gradoDeConfianza;
  private List<S2Usuario> miembros;
}
