package models.services.APIs.Servicio3.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Incidentes.Incidente;
import models.persistence.Persistente;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Setter
@Getter
public class S3Incidente extends S3Generica {
  private String horaApertura; //Formato 'yyyy-MM-ddThh:mm:ss' ISO LOCAL DATE TIME
  private String horaCierre; //Formato 'yyyy-MM-ddThh:mm:ss' ISO LOCAL DATE TIME
  private S3Establecimiento establecimiento;
  private S3Servicio servicio;

  public S3Incidente(Incidente incidente) {
    super(incidente);
    this.horaApertura = incidente.primeraApertura().getFechaYhora().toString();
    this.horaCierre = incidente.primerCierre().getFechaYhora().toString();
    this.establecimiento = new S3Establecimiento(incidente.getEstablecimiento());
    this.servicio = new S3Servicio(incidente.getServicio());
  }
}
