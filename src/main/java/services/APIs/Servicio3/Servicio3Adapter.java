package services.APIs.Servicio3;

import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Personas.Comunidad;

import java.util.List;

public interface Servicio3Adapter {
    void obtenerRanking(List<Entidad> entidades, List<Incidente> incidentes, List<Comunidad> comunidades);
}
