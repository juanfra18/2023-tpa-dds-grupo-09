package domain.Notificaciones;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import services.Archivos.SistemaDeArchivos;

import java.util.*;
import java.util.stream.Collectors;

public class EmisorDeNotificaciones{
    private static EmisorDeNotificaciones instancia = null;
    public static EmisorDeNotificaciones getInstancia() {
        if(instancia == null){
            instancia = new EmisorDeNotificaciones();
        }
        return instancia;
    }
  public void enviarNotificaciones(ReporteDeIncidente reporteDeIncidente, List<MiembroDeComunidad> miembros) {
    miembros.forEach(miembroDeComunidad -> miembroDeComunidad.recibirNotificacion(reporteDeIncidente));
    //el que envio recibe asi puede revisar bien que la informacion sea correcta
  }
  public void solicitarRevisionDeIncidente(List<Comunidad> comunidades){
     /*for(Comunidad comunidad : comunidades) {
         for(ReporteDeIncidente incidente : comunidad.incidentesAbiertos()){
             for(MiembroDeComunidad miembroDeComunidad : comunidad.getMiembros()){
                 miembroDeComunidad.recibirSolicitudDeRevision(incidente);
             }
         }
     }*/ //PROFE ESTA MEJOR EL FOR
     comunidades.forEach(comunidad -> comunidad.incidentesAbiertos().
             forEach(incidente -> comunidad.getMiembros().
                     forEach(miembroDeComunidad -> miembroDeComunidad.recibirSolicitudDeRevision(incidente))
     ));
  }

  /*
  public void generarRankings2(List<Comunidad> comunidades, List<Entidad> entidades){
    List<ReporteDeIncidente> incidentesDeComunidades = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentesDeLaComunidad().stream()).collect(Collectors.toList());
    List<ReporteDeIncidente> incidentesDeLaSemana = incidentesDeComunidades.stream().filter(reporteDeIncidente -> reporteDeIncidente.dentroDeEstaSemana()).collect(Collectors.toList());

    List<ReporteDeIncidente> incidentesAbiertos = comunidades.stream().flatMap(comunidad -> comunidad.incidentesAbiertos().stream()).collect(Collectors.toList());

    List<ReporteDeIncidente> incidentesRankeables = incidentesDeLaSemana.stream().filter(reporteDeIncidente -> !(reporteDeIncidente.nuevo() && incidentesAbiertos.contains(reporteDeIncidente))).collect(Collectors.toList());
    //no se tienen en cuenta aquellos incidentes ocurridos hace menos de 24 horas que sigan abiertos

    for(Entidad entidad : entidades)
    {
      List<ReporteDeIncidente> incidentesDeLaEntidad = incidentesRankeables.stream().filter(reporteDeIncidente -> reporteDeIncidente.getEntidad().equals(entidad)).collect(Collectors.toList());
      for(int i = 0; i < incidentesDeLaEntidad.stream().count(); i++)
        entidad.nuevoIncidente();
    }

      Collections.sort(entidades,Comparator.comparing(entidad -> entidad.getCantidadDeIncidentesPorSemana()));
    SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
    String[] encabezado = {"Nombre Entidad","Tipo Entidad","Cantidad de Incidentes reportados en la semana"};
    sistemaDeArchivos.escribirRanking(Config.RANKING_2,encabezado,entidades);
  }

/*  public static void main(String[] args) {
    EmisorDeNotificaciones emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();
    CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    AdapterServicioGeo servicioGeo = ServicioGeoref.instancia();
    RepositorioDeEmpresas repositorioDeEmpresas = new RepositorioDeEmpresas(cargadorDeDatos,servicioGeo);
    List<Entidad> entidades = repositorioDeEmpresas.getEmpresas().stream().flatMap(empresa -> empresa.getEntidadesPrestadoras().stream()).collect(Collectors.toList()).stream().flatMap(entidadPrestadora -> entidadPrestadora.getEntidades().stream()).collect(Collectors.toList());
  }
 */
}

