package models.services.APIs.Servicio3.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Servicios.Servicio;

@Setter
@Getter
public class S3Servicio extends S3Generica {
    public S3Servicio(Servicio servicio) {
        super(servicio);
    }
}
