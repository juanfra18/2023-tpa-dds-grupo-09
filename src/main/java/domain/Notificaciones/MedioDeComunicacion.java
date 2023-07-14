package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public interface MedioDeComunicacion {
  void recibirNotificacion(String mensaje, String asunto);
}
