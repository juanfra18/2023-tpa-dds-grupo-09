package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class EntidadesConMayorCantidadDeIncidentes implements Tierlist{
    /*Entidades con mayor cantidad de incidentes reportados en la semana.
    Una vez que un incidente sobre una prestación es reportado por algún usuario,
    independientemente de la comunidad de la que forma parte, no se consideran, para el presente ranking, ningún
    incidente que se genere sobre dicha prestación en un plazo de 24 horas siempre y cuando el mismo continúe abierto. */
    /*Se considera el período semanal desde el lunes a las 0.00 h. hasta el domingo 23.59 h.*/

    public void armarRanking(List<Entidad> entidades, List<ReporteDeIncidente> incidentes) {
        List<ReporteDeIncidente> incidentesConsiderados = incidentes.stream().filter(incidente -> this.seConsidera(incidente, incidentes)).toList();

        Set<ReporteDeIncidente> setIncidentes = new HashSet<>(); //para que no se repitan los incidentes
        incidentesConsiderados.forEach(incidente -> setIncidentes.add(incidente)); //Si hiciera collector.toset arriba no asegura preservar el orden
        List<ReporteDeIncidente> incidentesConsideradosSinRepeticion = setIncidentes.stream().toList();

        List<Entidad> entidadesPorCantidadDeIncidentes = entidades.stream().sorted(
            Comparator.comparingInt(entidad -> entidad.numeroDeIncidentes(incidentesConsideradosSinRepeticion)))
                .toList();

        List<String[]> listaStringEntidades = new ArrayList<>();

        entidadesPorCantidadDeIncidentes.forEach(entidad -> listaStringEntidades.add(new String[]
            {entidad.getNombre(),entidad.getTipoEntidad().toString(),entidad.numeroDeIncidentes(incidentesConsideradosSinRepeticion).toString()}));

        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        String[] encabezado = {"Nombre Entidad","Tipo Entidad","Cantidad de Incidentes reportados en la semana"};
        sistemaDeArchivos.escribirRanking(Config.RANKING_2, encabezado, listaStringEntidades);
    }
    private boolean seConsidera(ReporteDeIncidente incidente, List<ReporteDeIncidente> listaReportes){
        return incidente.cerrado() || (!incidente.cerrado() && !this.abiertoHaceMenosde24Horas(incidente, listaReportes));
    }
    private boolean abiertoHaceMenosde24Horas(ReporteDeIncidente incidente, List<ReporteDeIncidente> listaReportes){
        return listaReportes.stream().anyMatch(i->i.equals(incidente)&&
                Math.abs(ChronoUnit.HOURS.between(incidente.getFechaYhora(), i.getFechaYhora()))<24);
    }
}
