package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EntidadesConMayorCantidadDeIncidentes extends Tierlist{
    /*Entidades con mayor cantidad de incidentes reportados en la semana.
    Una vez que un incidente sobre una prestación es reportado por algún usuario,
    independientemente de la comunidad de la que forma parte, no se consideran, para el presente ranking, ningún
    incidente que se genere sobre dicha prestación en un plazo de 24 horas siempre y cuando el mismo continúe abierto. */
    /*Se considera el período semanal desde el lunes a las 0.00 h. hasta el domingo 23.59 h.*/

    public void armarRanking(List<Entidad> entidades, List<ReporteDeIncidente> incidentes){
        int[] contadorAux = new int[entidades.size()];
        List<ReporteDeIncidente> listaEspera = new ArrayList<>();

        for(ReporteDeIncidente reporteDeIncidente : incidentes) {
            if(!listaEspera.contains(reporteDeIncidente)) {
                List<ReporteDeIncidente> ListaAuxiliar = this.obtenerListaSinRepetirIncidente(incidentes, listaEspera, reporteDeIncidente);

                boolean abierto = false;
                LocalDateTime horarioIncidente = null;

                for (ReporteDeIncidente incidente : ListaAuxiliar ) {
                    if (!incidente.cerrado() && (!abierto || !this.abiertoHaceMenosde24Horas(incidente, horarioIncidente))){
                        horarioIncidente = incidente.getFechaYhora();
                        contadorAux[entidades.indexOf(incidente.getEntidad())]++;
                        abierto = true;
                    }
                    else if (incidente.cerrado()){
                        abierto = false;
                    }
                }
            }
        }

        List<Entidad> entidadesOrdenadas = this.ordenarEntidades(entidades, contadorAux);
        List<String[]> listaDeStrings = new ArrayList<>();
        entidadesOrdenadas.forEach(entidad ->
                listaDeStrings.add(new String[]
                        {entidad.getNombre(),entidad.getTipoEntidad().toString(),Integer.toString(contadorAux[entidades.indexOf(entidad)])}));

        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        String[] encabezado = {"Nombre Entidad","Tipo Entidad","Cantidad de Incidentes reportados en la semana"};
        sistemaDeArchivos.escribirRanking(Config.RANKING_2, encabezado, listaDeStrings);
    }
    private boolean abiertoHaceMenosde24Horas(ReporteDeIncidente incidente1, LocalDateTime horarioIncidente){
        return Math.abs(ChronoUnit.HOURS.between(incidente1.getFechaYhora(), horarioIncidente))<24;
    }

}

