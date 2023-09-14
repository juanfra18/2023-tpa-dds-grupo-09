package ServicioAPI;

import domain.Entidades.Entidad;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JsonResponse {
    List<Entidad> entidades;
}
