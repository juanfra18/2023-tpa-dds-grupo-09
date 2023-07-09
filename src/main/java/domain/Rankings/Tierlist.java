package domain.Rankings;

import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;

import java.util.List;

public interface Tierlist {
    void armarRanking(List<Entidad> entidades, List<ReporteDeIncidente> incidentes);
}
