package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;

public interface AdapterViaWPP {
  void recibirNotificacion(String mensaje, String telefonoDestinatario, String asunto);
}
