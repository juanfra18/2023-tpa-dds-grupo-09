package models.services.APIs.Servicio2;

import lombok.Getter;
import lombok.Setter;
import models.services.APIs.Servicio2.clases.S2Comunidad;

@Setter
@Getter
public class S2JsonResponseComunidad {
    private S2Comunidad comunidad;
    private Long nuevoPuntaje;
    private Long gradoDeConfianzaActual;
}
