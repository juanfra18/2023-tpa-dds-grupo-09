package domain.Rankings;

import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Tierlist {
    protected List<ReporteDeIncidente> obtenerListaSinRepetirIncidente(List<ReporteDeIncidente> incidentes, List<ReporteDeIncidente> listaEspera, ReporteDeIncidente reporteDeIncidente){
        List<ReporteDeIncidente> listaAuxiliar = new ArrayList<>(incidentes.stream().filter(incidente -> incidente.igualito(reporteDeIncidente)).toList());
        listaEspera.addAll(listaAuxiliar);
        Collections.sort(listaAuxiliar, new Comparator<ReporteDeIncidente>() {
            @Override
            public int compare(ReporteDeIncidente reporteDeIncidente1, ReporteDeIncidente reporteDeIncidente2) {
                return reporteDeIncidente1.getFechaYhora().compareTo(reporteDeIncidente2.getFechaYhora());
            }
        });
        return listaAuxiliar;
    }
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
