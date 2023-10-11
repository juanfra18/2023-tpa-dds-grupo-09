package models.services.APIs.Servicio2;

import lombok.Getter;
import lombok.Setter;
import models.services.APIs.Servicio2.clases.S2Usuario;

@Setter
@Getter
public class S2JsonResponseUsuario {
    private S2Usuario usuario;
    private Long nuevoPuntaje;
    private Long gradoDeConfianzaActual;
}
