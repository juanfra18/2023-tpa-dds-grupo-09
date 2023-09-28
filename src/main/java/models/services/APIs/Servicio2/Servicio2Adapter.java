package models.services.APIs.Servicio2;

import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;

public interface Servicio2Adapter {
  void enviarDatosGradoDeConfianzaComunidad(Comunidad comunidad);
  void enviarDatosGradoDeConfianzaMiembroDeComunidad(MiembroDeComunidad miembroDeComunidad);
}
