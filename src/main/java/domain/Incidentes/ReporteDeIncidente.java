package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Getter
public class ReporteDeIncidente {
  private final EstadoIncidente estado;
  private final LocalDateTime fechaYhora;
  private final MiembroDeComunidad denunciante;
  private final Establecimiento establecimiento;
  private final Servicio servicio;
  private final String observaciones;
  private final Entidad entidad;

  public ReporteDeIncidente(String estado, LocalDateTime fechaYhora, MiembroDeComunidad denunciante, Entidad entidad,
                            Establecimiento establecimiento, Servicio servicio, String observaciones) {
    this.estado = EstadoIncidente.valueOf(estado);
    this.fechaYhora = fechaYhora;
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
        && Objects.equals(this.servicio,otro.servicio);
  }

  public Boolean cerrado(){
    if(estado == EstadoIncidente.CERRADO)
      return true;
    else
      return false;
  }

  public String mensaje() {
    //TODO
    return this.estado.toString();
  }

  public boolean dentroDeEstaSemana() {
    LocalDateTime fechaActual = LocalDateTime.now();
    return ChronoUnit.DAYS.between(fechaActual,fechaYhora) <= 7;
  }

  public boolean nuevo() {
    LocalDateTime fechaActual = LocalDateTime.now();
    Duration tiempoDesdeElReporte = Duration.between(fechaYhora,fechaActual);
    return tiempoDesdeElReporte.toHours() < 24;
  }

}
