package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.Reglas.IncidenteAbierto;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmisorDeNotificaciones{
  private Comunidad comunidad;
  public EmisorDeNotificaciones(Comunidad comunidad) {
    this.comunidad = comunidad;
  }
  public void EnviarNotificaciones(ReporteDeIncidente reporteDeIncidente) {
    this.comunidad.getMiembros().forEach(miembroDeComunidad -> miembroDeComunidad.recibirNotificacion(reporteDeIncidente));
  }
  public void SolicitarRevisionDeIncidente(){
     for(int i = 0; i < comunidad.IncidentesAbiertos().size(); i++)
     {
       int index = i;
       List<Integer> distancias = comunidad.getMiembros().stream().map(miembroDeComunidad -> miembroDeComunidad.distancia(comunidad.IncidentesAbiertos().get(index))).toList();
       int distanciaMin = Collections.max(distancias);

       Optional<MiembroDeComunidad> miembroMasCerca = comunidad.getMiembros().stream().filter(miembroDeComunidad -> miembroDeComunidad.distancia(comunidad.IncidentesAbiertos().get(index)) == distanciaMin).findFirst();
       if(miembroMasCerca.isPresent())
       {
         //TODO
         //EL MIEMBRO MAS CERCA DEBERIA RECIBIR LA NOTIFICACION DE REVISION
       }
     }
  }
}
