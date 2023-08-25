package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntidadesQueSolucionanMasLento extends Tierlist{
/*Entidades con mayor promedio de tiempo de cierre de incidentes (diferencia entre horario de
cierre de incidente y horario de apertura) en la semana.
Este ranking es orientativo y puede no ser la tasa real de corrección de las fallas;*/

    public void armarRanking(List<Entidad> entidades,List<Incidente> incidentes) {
        int[] promedioAux = new int[entidades.size()]; //no buscamos la máxima precisión (float)
        int[] contadorAux = new int[entidades.size()];

        for(Incidente incidente: incidentes)
        {
            if(incidente.cerrado())
            {
                Optional<Entidad> entidadConIncidente = entidades.stream().filter(entidad -> entidad.getEstablecimientos().contains(incidente.getEstablecimiento())).findFirst();
                promedioAux[entidades.indexOf(entidadConIncidente.get())] += incidente.tiempoDeCierre();
                contadorAux[entidades.indexOf(entidadConIncidente.get())] ++; //aca no tenemos en cuenta la cantidad de dias abierto?
            }
        }

        for (int i = 0; i < entidades.size(); i++) {
            if (contadorAux[i] != 0)
                promedioAux[i] /= contadorAux[i];
        }

        List<Entidad> entidadesOrdenadas = this.ordenarEntidades(entidades, promedioAux);
        List<String[]> listaDeStrings = new ArrayList<>();
        entidadesOrdenadas.forEach(entidad ->
                listaDeStrings.add(new String[]
                        {entidad.getNombre(), entidad.getTipoEntidad().toString(), Integer.toString(promedioAux[entidades.indexOf(entidad)] / 60) + " horas," + Integer.toString(promedioAux[entidades.indexOf(entidad)] % 60) + " minutos"}));
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        sistemaDeArchivos.escribirRanking(Config.RANKING_1, new String[]{"Nombre Entidad", "Tipo Entidad", "Tiempo promedio de resolución de incidentes"}, listaDeStrings);
    }
}