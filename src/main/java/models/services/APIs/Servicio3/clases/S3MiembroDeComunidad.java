package models.services.APIs.Servicio3.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Personas.MiembroDeComunidad;
import models.persistence.Persistente;

import java.util.List;
@Setter
@Getter
public class S3MiembroDeComunidad extends S3Generica {
  private List<S3Servicio> serviciosQueAfectan;
  private List<S3Establecimiento> establecimientosDeInteres;

  public S3MiembroDeComunidad(MiembroDeComunidad miembroDeComunidad) {
    super(miembroDeComunidad);
    miembroDeComunidad.getEntidadesDeInteres().forEach(e -> e.getEstablecimientos().forEach(est -> this.establecimientosDeInteres.add(new S3Establecimiento(est))));
    miembroDeComunidad.getServiciosDeInteres().forEach(ps -> this.serviciosQueAfectan.add(new S3Servicio(ps.getServicio())));
  }
}
