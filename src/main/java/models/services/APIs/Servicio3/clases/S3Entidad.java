package models.services.APIs.Servicio3.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Entidades.Entidad;
import models.persistence.Persistente;

import java.util.List;
@Setter
@Getter
public class S3Entidad extends S3Generica {
  private List<S3Establecimiento> establecimientos;

  public S3Entidad(Entidad entidad) {
    super(entidad);
    entidad.getEstablecimientos().forEach(e -> this.establecimientos.add(new S3Establecimiento(e)));
  }
}
