package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

public interface AdapterViaWPP {
  void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, MiembroDeComunidad destinatario);
}
