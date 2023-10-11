package models.services.APIs.Servicio3.clases;

import lombok.Getter;
import lombok.Setter;
import models.persistence.Persistente;

@Setter
@Getter
public abstract class S3Generica {
  protected Long id;
  protected S3Generica(Persistente persistente) {
    this.id = persistente.getId();
  }
}
