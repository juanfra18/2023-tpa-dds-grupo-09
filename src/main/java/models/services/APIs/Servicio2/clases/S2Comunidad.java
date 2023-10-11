package models.services.APIs.Servicio2.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class S2Comunidad {
  private String gradoDeConfianza;
  private List<S2Usuario> miembros;

  public S2Comunidad(Comunidad comunidad) {
    List<S2Usuario> miembros = new ArrayList<>();
    comunidad.getMiembros().forEach(m -> miembros.add(new S2Usuario(m)));
    this.setMiembros(miembros);
    this.setGradoDeConfianza(comunidad.getGradosDeConfianza());
  }
}
