package domain.Rankings;

import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Tierlist {
    protected List<Entidad> ordenarEntidades(List<Entidad> entidades, int[] auxiliar){
        List<Entidad> entidadesOrdenadas = new ArrayList<>(entidades);
        Collections.sort(entidadesOrdenadas, new Comparator<Entidad>() {
            @Override
            public int compare(Entidad entidad1, Entidad entidad2) {
                int index1 = entidades.indexOf(entidad1);
                int index2 = entidades.indexOf(entidad2);
                return Integer.compare(auxiliar[index2], auxiliar[index1]);
            }
        });
        return entidadesOrdenadas;
    }
}
