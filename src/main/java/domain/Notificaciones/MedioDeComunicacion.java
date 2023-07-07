package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

public interface MedioDeComunicacion {
  void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, String destinatario, String asunto);
}
