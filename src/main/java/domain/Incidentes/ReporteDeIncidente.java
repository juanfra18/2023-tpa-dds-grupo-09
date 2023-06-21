package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import lombok.Getter;
import java.util.Date;

@Getter
public class ReporteDeIncidente {
  private final EstadoIncidente estado;
  private final Date fecha;
  private final int hora;
  private final MiembroDeComunidad denunciante;
  private final Establecimiento establecimiento;
  private final Servicio servicio;
  private final String observaciones;
  private final Entidad entidad;

  public ReporteDeIncidente(String estado, Date fecha, int hora, MiembroDeComunidad denunciante, Entidad entidad,
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
}
