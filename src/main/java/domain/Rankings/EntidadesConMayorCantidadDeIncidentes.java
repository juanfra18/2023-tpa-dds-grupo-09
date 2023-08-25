package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntidadesConMayorCantidadDeIncidentes extends Tierlist{
    /*Entidades con mayor cantidad de incidentes reportados en la semana.
    Una vez que un incidente sobre una prestación es reportado por algún usuario,
    independientemente de la comunidad de la que forma parte, no se consideran, para el presente ranking, ningún
    incidente que se genere sobre dicha prestación en un plazo de 24 horas siempre y cuando el mismo continúe abierto. */
    /*Se considera el período semanal desde el lunes a las 0.00 h. hasta el domingo 23.59 h.*/

    public void armarRanking(List<Entidad> entidades, List<Incidente> incidentes){
        int[] contadorAux = new int[entidades.size()];

        for(Incidente incidente : incidentes) {
            Optional<Entidad> entidadConIncidente = entidades.stream().filter(entidad -> entidad.getEstablecimientos().contains(incidente.getEstablecimiento())).findFirst();
            int cantidadDiasAbierto = incidente.diasAbierto();
            contadorAux[entidades.indexOf(entidadConIncidente.get())] += cantidadDiasAbierto;
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
}

