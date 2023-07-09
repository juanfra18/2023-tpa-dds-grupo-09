package domain.Rankings;

import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import domain.Incidentes.RepositorioDeIncidentes;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EntidadesConMayorCantidadDeIncidentes implements Tierlist{
    /*Entidades con mayor cantidad de incidentes reportados en la semana.
    Una vez que un incidente sobre una prestación es reportado por algún usuario,
    independientemente de la comunidad de la que forma parte, no se consideran, para el presente ranking, ningún
    incidente que se genere sobre dicha prestación en un plazo de 24 horas siempre y cuando el mismo continúe abierto. */
    /*Se considera el período semanal desde el lunes a las 0.00 h. hasta el domingo 23.59 h.*/

    public void armarRanking(List<Entidad> entidades, List<ReporteDeIncidente> incidentesConsiderados) {
         entidades.stream()
                .sorted(Comparator.comparingInt(e->incidentesConsiderados.stream().filter(
                        i->i.getEntidad().equals(e)).toList().size()))
                .collect(Collectors.toList());

         //TODO : devuelve una lista de entidades o llama a servicio csv para q cree el archivo
    }
    public boolean seConsidera(ReporteDeIncidente incidente){
        return incidente.cerrado() || !incidente.cerrado() && !this.abiertoHaceMenosde24Horas(incidente);
    }
    public boolean abiertoHaceMenosde24Horas(ReporteDeIncidente incidente){
        RepositorioDeIncidentes repositorioDeIncidentes= new RepositorioDeIncidentes();

        return repositorioDeIncidentes.getReportes().stream().anyMatch(i->i.equals(incidente)&&
                Math.abs(ChronoUnit.HOURS.between(incidente.getFechaYhora(), i.getFechaYhora()))<24);
    }
}
