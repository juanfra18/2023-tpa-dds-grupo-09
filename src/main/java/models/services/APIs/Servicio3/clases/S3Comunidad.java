package models.services.APIs.Servicio3.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import models.persistence.Persistente;

import java.util.List;
@Setter
@Getter
public class S3Comunidad extends S3Generica {
  private List<S3Incidente> incidentes;
  private List<S3MiembroDeComunidad> miembros;

  public S3Comunidad(Comunidad comunidad, List<Incidente> incidentes) {
    super(comunidad);
    comunidad.getMiembros().forEach(m -> this.miembros.add(new S3MiembroDeComunidad(m)));
    comunidad.getIncidentesDeComunidad(incidentes).forEach(i -> this.incidentes.add(new S3Incidente(i)));
  }
}
