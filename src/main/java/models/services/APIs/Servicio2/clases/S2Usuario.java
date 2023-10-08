package models.services.APIs.Servicio2.clases;

import lombok.Getter;
import lombok.Setter;
import models.domain.Personas.MiembroDeComunidad;

@Setter
@Getter
public class S2Usuario {
  private String nombre;
  private String apellido;
  private Long id;
  private Long puntosDeConfianza;
  public S2Usuario(MiembroDeComunidad miembroDeComunidad) {
    this.setId(miembroDeComunidad.getId());
    this.setNombre(miembroDeComunidad.getNombre());
    this.setApellido(miembroDeComunidad.getApellido());
    this.setPuntosDeConfianza(miembroDeComunidad.getPuntosDeConfianza());
  }
}
