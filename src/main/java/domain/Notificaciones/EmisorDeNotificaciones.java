package domain.Notificaciones;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.EntidadPrestadora;
import domain.Entidades.RepositorioDeEmpresas;
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


  public void generarRankings (RepositorioDeEmpresas repositorioDeEmpresas, RepositorioDeIncidentes repositorioDeIncidentes)
  {
      List<EntidadPrestadora> entidadesPrestadoras = repositorioDeEmpresas.getEmpresas().stream().flatMap(organismoDeControl -> organismoDeControl.getEntidadesPrestadoras().stream()).toList()
      List<Entidad> entidades = entidadesPrestadoras.stream().flatMap(entidadPrestadora -> entidadPrestadora.getEntidades().stream()).toList();

      List<ReporteDeIncidente> reportesDeEstaSemana = repositorioDeIncidentes.getIncidentesEstaSemana();

      entidadesConMayorCantidadDeIncidentes.armarRanking(entidades,reportesDeEstaSemana);
      entidadesQueSolucionanMasLento.armarRanking(entidades,reportesDeEstaSemana);

      repositorioDeEmpresas.getEmpresas().forEach(organismoDeControl -> organismoDeControl.recibirInforme(Config.RANKING_1,"INFORME SEMANAL: Entidades que resuelven mas lento"));
      repositorioDeEmpresas.getEmpresas().forEach(organismoDeControl -> organismoDeControl.recibirInforme(Config.RANKING_2,"INFORME SEMANAL: Entidades con mayor cantidad de incidentes"));

      entidadesPrestadoras.forEach(entidadPrestadora -> entidadPrestadora.recibirInforme(Config.RANKING_1,"INFORME SEMANAL: Entidades que resuelven mas lento"));
      entidadesPrestadoras.forEach(entidadPrestadora -> entidadPrestadora.recibirInforme(Config.RANKING_2,"INFORME SEMANAL: Entidades con mayor cantidad de incidentes"));

  }


}

