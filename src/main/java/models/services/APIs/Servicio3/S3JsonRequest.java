package models.services.APIs.Servicio3;

import lombok.Getter;
import lombok.Setter;
import models.services.APIs.Servicio3.clases.S3Comunidad;
import models.services.APIs.Servicio3.clases.S3Entidad;
import models.services.APIs.Servicio3.clases.S3Incidente;

import java.util.List;

@Setter
@Getter
public class S3JsonRequest {
    private Long CNF;
    private List<S3Entidad> entidades;
    private List<S3Incidente> incidentes;
    private List<S3Comunidad> comunidades;
}
