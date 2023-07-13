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
        List<ReporteDeIncidente> ListaEspera = new ArrayList<>();

        for(ReporteDeIncidente reporteDeIncidente : incidentes)
        {
            if(!ListaEspera.contains(reporteDeIncidente))
            {
                List<ReporteDeIncidente> ListaAuxiliar = incidentes.stream().filter(incidente -> incidente.equals(reporteDeIncidente)).toList();

                ListaEspera.addAll(ListaAuxiliar);

                List<ReporteDeIncidente> ListaMutable = new ArrayList<>(ListaAuxiliar); //no me dejaba modificar la lista auxiliar ->inmutable
                Collections.sort(ListaMutable, new Comparator<ReporteDeIncidente>() {
                    @Override
                    public int compare(ReporteDeIncidente incidente1, ReporteDeIncidente incidente2) {
                        return incidente1.getFechaYhora().compareTo(incidente2.getFechaYhora());
                    }
                });

                boolean abierto = false;
                LocalDateTime horarioIncidente = null;

                for(int i = 0; i < ListaMutable.size(); i++) //por qué el i y no incidente: listaauxiliar
                {
                    if(!ListaMutable.get(i).cerrado() && (!abierto || !abiertoHaceMenosde24Horas(ListaMutable.get(i),horarioIncidente)))
                    {
                        horarioIncidente = ListaMutable.get(i).getFechaYhora();
                        contadorAux[entidades.indexOf(ListaMutable.get(i).getEntidad())]++;
                        abierto = true;
                    }

                    else if(ListaMutable.get(i).cerrado())
                    {
                        abierto = false;
                    }
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
        });

        List<String[]> listaDeStrings = new ArrayList<>();
        for(Entidad entidad : entidades) //foreach?
        {
            listaDeStrings.add(new String[]{entidad.getNombre(),entidad.getTipoEntidad().toString(),Integer.toString(contadorAux[entidades.indexOf(entidad)])});
        }
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        String[] encabezado = {"Nombre Entidad","Tipo Entidad","Cantidad de Incidentes reportados en la semana"};
        sistemaDeArchivos.escribirRanking(Config.RANKING_2, encabezado, listaDeStrings);
    }





    private boolean abiertoHaceMenosde24Horas(ReporteDeIncidente incidente1, LocalDateTime horarioIncidente){
        return Math.abs(ChronoUnit.HOURS.between(incidente1.getFechaYhora(), horarioIncidente))<24;
    }

    private boolean abiertoHaceMenosde24Horas(ReporteDeIncidente incidente){
        return Math.abs(ChronoUnit.HOURS.between(incidente.getFechaYhora(), LocalDateTime.now()))<24;
    }
}

