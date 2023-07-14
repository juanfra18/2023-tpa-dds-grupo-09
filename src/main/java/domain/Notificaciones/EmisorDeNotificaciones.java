package domain.Notificaciones;

import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import domain.Incidentes.RepositorioDeIncidentes;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import domain.Rankings.EntidadesQueSolucionanMasLento;

import java.util.List;

public class EmisorDeNotificaciones {
    private static EmisorDeNotificaciones instancia = null;
    private EmisorDeNotificaciones(){

    }
    public static EmisorDeNotificaciones getInstancia() {
        if(instancia == null){
            instancia = new EmisorDeNotificaciones();
        }
        return instancia;
    }
    EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes;
    EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento;
  public void enviarNotificaciones(ReporteDeIncidente reporteDeIncidente, List<MiembroDeComunidad> miembros) {
    miembros.forEach(miembroDeComunidad -> miembroDeComunidad.recibirNotificacion(reporteDeIncidente));
    //el que envio recibe asi puede revisar bien que la informacion sea correcta
  }
  public void solicitarRevisionDeIncidente(List<Comunidad> comunidades){
     comunidades.forEach(comunidad -> comunidad.incidentesAbiertos().
             forEach(incidente -> comunidad.getMiembros().
                     forEach(miembroDeComunidad -> miembroDeComunidad.recibirSolicitudDeRevision(incidente))
     ));
  } //un main llama a esto cada cierto tiempo


  public void generarRankings (List<Entidad> entidades, RepositorioDeIncidentes repositorioDeIncidentes)
  {
      List<ReporteDeIncidente> reportesDeEstaSemana = repositorioDeIncidentes.getIncidentesEstaSemana();

      entidadesConMayorCantidadDeIncidentes.armarRanking(entidades,reportesDeEstaSemana);
      entidadesQueSolucionanMasLento.armarRanking(entidades,reportesDeEstaSemana);

  }

}

