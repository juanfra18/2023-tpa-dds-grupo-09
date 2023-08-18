package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntidadesQueSolucionanMasLento extends Tierlist{
/*Entidades con mayor promedio de tiempo de cierre de incidentes (diferencia entre horario de
cierre de incidente y horario de apertura) en la semana.
Este ranking es orientativo y puede no ser la tasa real de corrección de las fallas;*/

    public void armarRanking(List<Entidad> entidades,List<ReporteDeIncidente> incidentes) {
        int[] promedioAux = new int[entidades.size()]; //no buscamos la máxima precisión (float)
        int[] contadorAux = new int[entidades.size()];
        List<ReporteDeIncidente> listaEspera = new ArrayList<>();

        for(ReporteDeIncidente reporteDeIncidente : incidentes) {
            if (!listaEspera.contains(reporteDeIncidente)) {

                List<ReporteDeIncidente> listaAuxiliar = this.obtenerListaSinRepetirIncidente(incidentes, listaEspera, reporteDeIncidente);

                boolean abierto = false;
                LocalDateTime tiempoApertura = LocalDateTime.now();

                for (ReporteDeIncidente incidente : listaAuxiliar) {
                    if (!incidente.esDeCierre() && !abierto) {
                        tiempoApertura = incidente.getFechaYhora();
                        abierto = true;
                    } else if (incidente.esDeCierre() && abierto) {
                        LocalDateTime tiempoCierre = incidente.getFechaYhora();
                        Duration tiempoQueTardoEnCerrarse = Duration.between(tiempoApertura, tiempoCierre);
                        promedioAux[entidades.indexOf(incidente.getEntidad())] += tiempoQueTardoEnCerrarse.getSeconds();
                        contadorAux[entidades.indexOf(incidente.getEntidad())]++;
                        abierto = false;
                    }
                }
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
                        {entidad.getNombre(), entidad.getTipoEntidad().toString(), Integer.toString(promedioAux[entidades.indexOf(entidad)] / 3600) + " horas," + Integer.toString(promedioAux[entidades.indexOf(entidad)] % 3600 / 60) + " minutos"}));
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        sistemaDeArchivos.escribirRanking(Config.RANKING_1, new String[]{"Nombre Entidad", "Tipo Entidad", "Tiempo promedio de resolución de incidentes"}, listaDeStrings);
    }
}