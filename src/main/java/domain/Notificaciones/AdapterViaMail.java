package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

public interface AdapterViaMail {
  void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, String mailDestinatario, String asunto);
  void enviarArchivo(String ruta, String mailDestinatario, String asunto);
}
