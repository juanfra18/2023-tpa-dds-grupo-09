package models.services.APIs.Servicio2.clases;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class S2Incidente {
  private Long id;
  private String fechaApertura;
  private S2Usuario usuarioReportador;
  private String fechaCierre;
  private S2Usuario usuarioAnalizador;
  private S2Servicio servicioAfectado;
}
