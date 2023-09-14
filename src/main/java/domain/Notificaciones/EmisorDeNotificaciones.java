package domain.Notificaciones;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.EntidadPrestadora;
import domain.Entidades.OrganismoDeControl;
import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import persistence.Repositorios.RepositorioDeEntidadPrestadora;
import persistence.Repositorios.RepositorioDeIncidentes;
import persistence.Repositorios.RepositorioDeOrganismosDeControl;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import domain.Rankings.EntidadesQueSolucionanMasLento;
import persistence.Repositorios.RepositorioEntidad;

import java.util.ArrayList;
import java.util.List;

public class EmisorDeNotificaciones {
  private static EmisorDeNotificaciones instancia = null;
  private EmisorDeNotificaciones(){
    entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
    entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
  }
  public static EmisorDeNotificaciones getInstancia() {
    if(instancia == null){
      instancia = new EmisorDeNotificaciones();
    }
    return instancia;
  }
  private EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes;
  private EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento;

  public void enviarNotificaciones(ReporteDeIncidente reporteDeIncidente, List<MiembroDeComunidad> miembros) {
    miembros.forEach(miembroDeComunidad -> miembroDeComunidad.recibirNotificacion(reporteDeIncidente));
    //el que envio recibe asi puede revisar bien que la informacion sea correcta
  }
  public void solicitarRevisionDeIncidente(List<Comunidad> comunidades, List<Incidente> incidentes){
    comunidades.forEach(comunidad -> comunidad.incidentesAbiertos(incidentes).
        forEach(incidente -> comunidad.getMiembros().
            forEach(miembroDeComunidad -> miembroDeComunidad.recibirSolicitudDeRevision(incidente.primeraApertura()))
        ));
  } //un main llama a esto cada cierto tiempo


  public void generarRankings ()
  {
    RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
    RepositorioDeEntidadPrestadora repositorioDeEntidadPrestadora = RepositorioDeEntidadPrestadora.getInstancia();
    RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();

    List<OrganismoDeControl> organismosDeControl = repositorioDeOrganismosDeControl.buscarTodos();
    List<EntidadPrestadora> entidadesPrestadoras = repositorioDeEntidadPrestadora.buscarTodos();
    List<Entidad> entidades = repositorioEntidad.buscarTodos();
    List<Incidente> incidentesDeEstaSemana = repositorioDeIncidentes.getIncidentesEstaSemana();

    this.entidadesConMayorCantidadDeIncidentes.armarRanking(entidades,incidentesDeEstaSemana);
    this.entidadesQueSolucionanMasLento.armarRanking(entidades,incidentesDeEstaSemana);


    organismosDeControl.forEach(organismoDeControl -> organismoDeControl.recibirInforme(Config.RANKING_1,"INFORME SEMANAL: Entidades que resuelven mas lento"));
    organismosDeControl.forEach(organismoDeControl -> organismoDeControl.recibirInforme(Config.RANKING_2,"INFORME SEMANAL: Entidades con mayor cantidad de incidentes"));

    entidadesPrestadoras.forEach(entidadPrestadora -> entidadPrestadora.recibirInforme(Config.RANKING_1,"INFORME SEMANAL: Entidades que resuelven mas lento"));
    entidadesPrestadoras.forEach(entidadPrestadora -> entidadPrestadora.recibirInforme(Config.RANKING_2,"INFORME SEMANAL: Entidades con mayor cantidad de incidentes"));

  }


}
