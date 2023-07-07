package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

public class ViaMail implements MedioDeComunicacion{
  private AdapterViaMail viaMail;
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, String mailDestinatario, String asunto) {
    //TODO
  }
}