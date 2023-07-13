package domain.Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import services.Archivos.SistemaDeArchivos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntidadesQueSolucionanMasLento implements Tierlist{
/*Entidades con mayor promedio de tiempo de cierre de incidentes (diferencia entre horario de
cierre de incidente y horario de apertura) en la semana.
Este ranking es orientativo y puede no ser la tasa real de corrección de las fallas;*/

    public void armarRanking(List<Entidad> entidades,List<ReporteDeIncidente> incidentes) {
        int[] promedioAux = new int[entidades.size()]; //no buscamos la máxima precisión (float)
        int[] contadorAux = new int[entidades.size()];
        List<ReporteDeIncidente> ListaEspera = new ArrayList<>();

        for(ReporteDeIncidente reporteDeIncidente : incidentes) {
            if (!ListaEspera.contains(reporteDeIncidente)) {
                List<ReporteDeIncidente> ListaAuxiliar = new ArrayList<>(incidentes.stream().filter(incidente -> incidente.equals(reporteDeIncidente)).toList());

                ListaEspera.addAll(ListaAuxiliar);
                Collections.sort(ListaAuxiliar, new Comparator<ReporteDeIncidente>() {
                    @Override
                    public int compare(ReporteDeIncidente reporteDeIncidente1, ReporteDeIncidente reporteDeIncidente2) {
                        return reporteDeIncidente1.getFechaYhora().compareTo(reporteDeIncidente2.getFechaYhora());
                    }
                });


                boolean abierto = false;
                LocalDateTime tiempoApertura=LocalDateTime.now();

                for (int i = 0; i < ListaAuxiliar.size(); i++) {
                    if (!ListaAuxiliar.get(i).cerrado() && !abierto) {
                        tiempoApertura = ListaAuxiliar.get(i).getFechaYhora();
                        abierto = true;
                    } else if (ListaAuxiliar.get(i).cerrado() && abierto) {
                        LocalDateTime tiempoCierre = ListaAuxiliar.get(i).getFechaYhora();
                        Duration tiempoQueTardoEnCerrarse = Duration.between(tiempoApertura, tiempoCierre);
                        promedioAux[entidades.indexOf(ListaAuxiliar.get(i).getEntidad())] += tiempoQueTardoEnCerrarse.getSeconds();
                        contadorAux[entidades.indexOf(ListaAuxiliar.get(i).getEntidad())]++;
                        abierto = false;
                    }
                }
                for (int i = 0; i < entidades.size(); i++) {
                    if (contadorAux[i] != 0)
                        promedioAux[i] /= contadorAux[i];

                }
            }
            List<Entidad> entidadesOrdenadas = new ArrayList<>(entidades);

            Collections.sort(entidadesOrdenadas, new Comparator<Entidad>() {
                @Override
                public int compare(Entidad entidad1, Entidad entidad2) {
                    int index1 = entidades.indexOf(entidad1);
                    int index2 = entidades.indexOf(entidad2);
                    return Integer.compare(promedioAux[index2], promedioAux[index1]);
                }
            });
            /*
        //el repositorio de incidentes se encarga de obtener los de esta semana. Acá llegan esos ya filtrados
        //por cada entidad, ver su promedio de cierre de incidentes, hacer un map clave: nombreentidad, valor: promedio y subirlo al csv
        Map<Entidad, Integer> entidadPromedioCierre = new HashMap<>();

        //TODO falta filtrar la lista de incidentes que llega para cumplir con las condiciones en
        // reportedeincidente.tiempodecierre, así se puede usar ese método y armar el hashmap con
        // cada entidad y su tiempo promedio correspondiente
        */

            List<String[]> listaDeStrings = new ArrayList<>();
            for (Entidad entidad : entidadesOrdenadas) {
                listaDeStrings.add(new String[]{entidad.getNombre(), entidad.getTipoEntidad().toString(), Integer.toString(promedioAux[entidades.indexOf(entidad)] / 3600) + " horas," + Integer.toString(promedioAux[entidades.indexOf(entidad)] % 3600 / 60) + " minutos"});

            }
            SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
            String[] encabezado = {"Nombre Entidad", "Tipo Entidad", "Tiempo promedio de resolución de incidentes"};
            sistemaDeArchivos.escribirRanking(Config.RANKING_1, encabezado, listaDeStrings);
        }
    }
}
