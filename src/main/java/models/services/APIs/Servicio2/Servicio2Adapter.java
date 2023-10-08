package models.services.APIs.Servicio2;

import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;

import java.util.List;

public interface Servicio2Adapter {
  void enviarDatosGradoDeConfianzaComunidad(Comunidad comunidad, List<Incidente> incidentes);
  void enviarDatosGradoDeConfianzaMiembroDeComunidad(MiembroDeComunidad miembroDeComunidad);
}
