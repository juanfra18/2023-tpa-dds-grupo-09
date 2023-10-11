package models.services.APIs.Servicio2;

import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;

import java.util.List;

public interface Servicio2Adapter {
  Long obtenerGradoDeConfianzaComunidad(Comunidad comunidad, List<Incidente> incidentes);
  Long obtenerGradoDeConfianzaMiembroDeComunidad(MiembroDeComunidad miembroDeComunidad, List<Incidente> incidentes);
}
