package domain.Rankings;

import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntidadesQueSolucionanMasLento implements Tierlist{
/*Entidades con mayor promedio de tiempo de cierre de incidentes (diferencia entre horario de
cierre de incidente y horario de apertura) en la semana.
Este ranking es orientativo y puede no ser la tasa real de corrección de las fallas;*/

    public void armarRanking(List<Entidad> entidades,List<ReporteDeIncidente> incidentes) {
        //el repositorio de incidentes se encarga de obtener los de esta semana. Acá llegan esos ya filtrados
        //por cada entidad, ver su promedio de cierre de incidentes, hacer un map clave: nombreentidad, valor: promedio y subirlo al csv
        Map<Entidad, Integer> entidadPromedioCierre = new HashMap<>();

        //TODO falta filtrar la lista de incidentes que llega para cumplir con las condiciones en
        // reportedeincidente.tiempodecierre, así se puede usar ese método y armar el hashmap con
        // cada entidad y su tiempo promedio correspondiente
    }
}