package models.services.APIs.Servicio2.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Incidentes.Incidente;

import java.time.format.DateTimeFormatter;


@Setter
@Getter
public class S2Incidente {
  private Long id;
  private String fechaApertura;
  private S2Usuario usuarioReportador;
  private String fechaCierre;
  private S2Usuario usuarioAnalizador;
  private S2Servicio servicioAfectado;

  public S2Incidente(Incidente incidente) {
    this.setId(incidente.getId());

    S2Servicio servicio = new S2Servicio();
    servicio.setId(incidente.getServicio().getId());
    this.setServicioAfectado(servicio);

    this.setUsuarioReportador(new S2Usuario(incidente.primeraApertura().getDenunciante()));

    this.setFechaApertura(incidente.primeraApertura().getFechaYhora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));

    this.setFechaCierre(incidente.primerCierre().getFechaYhora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));

    this.setUsuarioAnalizador(new S2Usuario(incidente.primerCierre().getDenunciante()));
  }
}
