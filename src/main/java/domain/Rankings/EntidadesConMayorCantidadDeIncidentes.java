package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntidadesConMayorCantidadDeIncidentes implements Tierlist{
    /*Entidades con mayor cantidad de incidentes reportados en la semana.
    Una vez que un incidente sobre una prestación es reportado por algún usuario,
    independientemente de la comunidad de la que forma parte, no se consideran, para el presente ranking, ningún
    incidente que se genere sobre dicha prestación en un plazo de 24 horas siempre y cuando el mismo continúe abierto. */
    /*Se considera el período semanal desde el lunes a las 0.00 h. hasta el domingo 23.59 h.*/

    public void armarRanking(List<Entidad> entidades, List<ReporteDeIncidente> incidentes){
        int[] contadorAux = new int[entidades.size()];

        for(ReporteDeIncidente reporteDeIncidente : incidentes)
        {
            List<ReporteDeIncidente> ListaAuxiliar = incidentes.stream().filter(incidente -> incidente.equals(reporteDeIncidente)).toList();
            ListaAuxiliar.forEach(incidente -> incidentes.remove(incidente)); //se podría hacer de una el filter con !equals. Igual raro modificar la lista sobre la que iterás
            Collections.sort(ListaAuxiliar, new Comparator<ReporteDeIncidente>() {
                @Override
                public int compare(ReporteDeIncidente reporteDeIncidente1, ReporteDeIncidente reporteDeIncidente2){
                    return reporteDeIncidente1.getFechaYhora().compareTo(reporteDeIncidente2.getFechaYhora());
                }
            }); //por qué ordenarlo por fecha? se supone que llegan y se pone la fecha actual

            boolean abierto = false;
            LocalDateTime horarioIncidente = null;

            for(int i = 0; i < ListaAuxiliar.size(); i++) //por qué el i y no incidente: listaauxiliar
            {
                if(!ListaAuxiliar.get(i).cerrado() && (!abierto || abiertoHaceMenosde24Horas(ListaAuxiliar.get(i),horarioIncidente)))
                {
                    horarioIncidente = ListaAuxiliar.get(i).getFechaYhora();
                    contadorAux[entidades.indexOf(ListaAuxiliar.get(i).getEntidad())]++;
                    abierto = true;
                }

                else if(ListaAuxiliar.get(i).cerrado())
                {
                    abierto = false;
                }
            }
        }
        Collections.sort(entidades, new Comparator<Entidad>() {
            @Override
            public int compare(Entidad entidad1, Entidad entidad2) {
                int index1 = entidades.indexOf(entidad1);
                int index2 = entidades.indexOf(entidad2);
                return Integer.compare(contadorAux[index1], contadorAux[index2]);
            }
        }); //qué es contadorAux? según qué ordena?

       /* List<ReporteDeIncidente> incidentesConsiderados = incidentes.stream().filter(incidente -> this.seConsidera(incidente, incidentes)).toList();

        Set<ReporteDeIncidente> setIncidentes = new HashSet<>(); //para que no se repitan los incidentes
        incidentesConsiderados.forEach(incidente -> setIncidentes.add(incidente)); //Si hiciera collector.toset arriba no asegura preservar el orden
        List<ReporteDeIncidente> incidentesConsideradosSinRepeticion = setIncidentes.stream().toList();

        List<Entidad> entidadesPorCantidadDeIncidentes = entidades.stream().sorted(
            Comparator.comparingInt(entidad -> entidad.numeroDeIncidentes(incidentesConsideradosSinRepeticion)))
                .toList();

        List<String[]> listaStringEntidades = new ArrayList<>();

        entidadesPorCantidadDeIncidentes.forEach(entidad -> listaStringEntidades.add(new String[]
            {entidad.getNombre(),entidad.getTipoEntidad().toString(),entidad.numeroDeIncidentes(incidentesConsideradosSinRepeticion).toString()}));
*/
        List<String[]> listaDeStrings = new ArrayList<>();
        for(Entidad entidad : entidades) //foreach?
        {
            listaDeStrings.add(new String[]{entidad.getNombre(),entidad.getTipoEntidad().toString(),Integer.toString(contadorAux[entidades.indexOf(entidad)])});
        }
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        String[] encabezado = {"Nombre Entidad","Tipo Entidad","Cantidad de Incidentes reportados en la semana"};
        sistemaDeArchivos.escribirRanking(Config.RANKING_2, encabezado, listaDeStrings);
    }


    /*private boolean seConsidera(ReporteDeIncidente incidente, List<ReporteDeIncidente> listaReportes){
        return incidente.cerrado() || (!incidente.cerrado() && !this.abiertoHaceMenosde24Horas(incidente, listaReportes));
    }*/
    private boolean abiertoHaceMenosde24Horas(ReporteDeIncidente incidente1, LocalDateTime horarioIncidente){
        return Math.abs(ChronoUnit.HOURS.between(incidente1.getFechaYhora(), horarioIncidente))<24;
    }
}

