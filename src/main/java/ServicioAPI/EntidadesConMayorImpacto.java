package ServicioAPI;

import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;

import java.util.List;
import java.util.Optional;

public class EntidadesConMayorImpacto {
    /* Servicio 3: cálculo de ranking de impacto de incidentes
Este servicio permite calcular el ranking semanal previsto en la entrega anterior con el criterio de mayor grado de impacto de las problemáticas considerando
que algunas comunidades tienen mayor cantidad de miembros y por lo tanto les afecta de mayor medida el no funcionamiento de ese servicio.
Para cada entidad se calcula su nivel de impacto considerando la sumatoria de tiempos de resolución de incidentes + la cantidad de incidentes no resueltos
multiplicado por un coeficiente de incidentes no resueltos (CNF), expresado en la siguiente ecuación

Σ (t resolución de incidente) + Cantidad de incidentes no resueltos * CNF

A la ecuación anterior se la debe multiplicar por la cantidad de miembros afectados para obtener el resultado final.
Con los valores obtenidos se realiza un ordenamiento de las entidades.
Requerimientos detallados
Se deberán persistir las entidades del modelo planteado. Para ello se debe utilizar un ORM.
Se deberá implementar el servicio que tuviera asignado el grupo

     */
    // nivelDeImpacto = Σ (t resolución de incidente) + Cantidad de incidentes no resueltos * CNF
    // total = nivelDeImpacto * cantMiembros (miembros.afectados.size())
    // total -> Armas el ranking

    protected int[] obtenerValoresPorEntidad(List<Entidad> entidades, List<Incidente> incidentes, List<Comunidad> comunidades) {
        int[] tiempoDeResolucion = new int[entidades.size()];
        int[] cantIncidentesNoResueltos = new int[entidades.size()];
        int[] impactoDeIncidentes = new int[entidades.size()];
        final int CNF = 10;
        int[] cantMiembrosAfectados = new int[entidades.size()];

        for(Incidente incidente: incidentes) //Buscamos la primer entidad con ese incidete
        {
            Optional<Entidad> entidadConIncidente = entidades.stream().filter(entidad -> entidad.getEstablecimientos().contains(incidente.getEstablecimiento())).findFirst();
            if(incidente.cerrado())
            {
                tiempoDeResolucion[entidades.indexOf(entidadConIncidente.get())] += incidente.tiempoDeCierre(); //se acumula el tiempo de resolucion de incidentes en minutos
            }
            else{
                cantIncidentesNoResueltos[entidades.indexOf(entidadConIncidente.get())]++;
                //No se consideran como en el primer ranking 2 incidentes con mas de 24 horas de diferencia como 2 incidentes distintos

                List<Comunidad> comunidadesAfectadas = comunidades.stream().filter(comunidad -> comunidad.reportaronIncidente(incidente)).toList();
                List<MiembroDeComunidad> MiembrosRepetidos = comunidadesAfectadas.stream().flatMap(comunidad -> comunidad.getMiembros().stream()).toList();
                List<MiembroDeComunidad> MiembrosDeComunidadesAfectados = null;
                for(MiembroDeComunidad miembroDeComunidad:MiembrosRepetidos)
                {
                    if(!MiembrosDeComunidadesAfectados.contains(miembroDeComunidad) && miembroDeComunidad.afectadoPor(incidente)) //A cada uno de los miemmbros lo agrega a las comunidades afectadas
                    {
                        MiembrosDeComunidadesAfectados.add(miembroDeComunidad);
                    }
                }

                cantMiembrosAfectados[entidades.indexOf(entidadConIncidente.get())]+= MiembrosDeComunidadesAfectados.size(); //Cantidad de miembrosAfectados
            }
        }

        for(int i = 0; i < entidades.size();i++) //Σ (t resolución de incidente) + Cantidad de incidentes no resueltos * CNF * cantMiembros (miembros.afectados.size())
        {
            impactoDeIncidentes[i] = (tiempoDeResolucion[i] + cantIncidentesNoResueltos[i] * CNF) * cantMiembrosAfectados[i];
        }

        return impactoDeIncidentes;
    }

    protected void generarRanking(List<Entidad> entidadesOrdenadas, List<Entidad> entidades, int[] promedioAux) {

    }


}
