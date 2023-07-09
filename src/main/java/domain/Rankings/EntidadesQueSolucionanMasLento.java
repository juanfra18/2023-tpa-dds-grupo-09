package domain.Rankings;

import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;

import java.util.List;

public class EntidadesQueSolucionanMasLento implements Tierlist{
/*Entidades con mayor promedio de tiempo de cierre de incidentes (diferencia entre horario de
cierre de incidente y horario de apertura) en la semana.
Este ranking es orientativo y puede no ser la tasa real de corrección de las fallas;*/

    //HACER UN REPO SEMANAL DE INCIDENTES Y AL FINAL DE LA SEMANA SUBIRLO A UNO GLOBAL Y VACIARLO (?
    public void armarRanking(List<Entidad> entidades,List<ReporteDeIncidente> incidentes) {

    }
}