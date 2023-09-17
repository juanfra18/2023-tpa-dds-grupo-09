package ServicioAPI;

import ServicioAPI.domain.APIEntidad;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JsonResponse {
    List<APIEntidad> entidades;
}
