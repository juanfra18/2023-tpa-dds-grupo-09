package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
}
