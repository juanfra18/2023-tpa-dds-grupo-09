package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import lombok.Getter;
import java.util.Date;
import java.util.Objects;

@Getter
public class ReporteDeIncidente {
  private final EstadoIncidente estado;
  private final Date fecha;
  private final Date hora;
  private final MiembroDeComunidad denunciante;
  private final Establecimiento establecimiento;
  private final Servicio servicio;
  private final String observaciones;
  private final Entidad entidad;

  public ReporteDeIncidente(String estado, Date fecha, Date hora, MiembroDeComunidad denunciante, Entidad entidad,
                            Establecimiento establecimiento, Servicio servicio, String observaciones) {
    this.estado = EstadoIncidente.valueOf(estado);
    this.fecha = fecha;
    this.hora = hora;
    this.denunciante = denunciante;
    this.establecimiento = establecimiento;
    this.servicio = servicio;
    this.observaciones = observaciones;
    this.entidad = entidad;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ReporteDeIncidente otro = (ReporteDeIncidente) obj;
    return Objects.equals(this.establecimiento, otro.establecimiento)
        && Objects.equals(this.servicio,otro.servicio)
        && Objects.equals(this.getEstado(),otro.getEstado());
  }
}
