package ServicioAPI;

import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Personas.Comunidad;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JsonRequest {
    private List<Entidad> entidades;
    private List<Incidente> incidentes;
    private List<Comunidad> comunidades;

    public JsonRequest() {
        this.entidades = new ArrayList<>();
        this.incidentes = new ArrayList<>();
        this.comunidades = new ArrayList<>();
    }
}


/* En este caso se le envía un json con las 3 listas (entidades, incidentes y comunidades)

    {
    "entidades": [
        {
           //entidad1
        },
        {
            //entidad2
        }
    ],
    "incidentes": [
        {
            // incidente1
        },
        {
            // incidente2
        }
    ],
    "comunidades": [
        {
            // comunidad1
        },
        {
            // comunidadN
        }
    ]
}
*/