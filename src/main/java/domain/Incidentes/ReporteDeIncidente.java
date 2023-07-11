package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    return "Incidente en "+ this.getEstablecimiento().getNombre()+", " +
        "en el servicio "+ this.getServicio().getTipo()+"." +
        "\nObservaciones: "+this.getObservaciones();
  }

  public boolean dentroDeEstaSemana() {
    LocalDateTime inicioDeSemana = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDateTime finalDeSemana = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    return this.getFechaYhora().isAfter(inicioDeSemana) && this.getFechaYhora().isBefore(finalDeSemana);
  }

  public Long tiempoDeCierre(ReporteDeIncidente incidente, List<ReporteDeIncidente> incidentes) {
    //asume que ya le llega la lista con 1 reporte de apertura y 1 de cierre por incidente
    return ChronoUnit.HOURS.between(incidentes.stream().filter(reporteDeIncidente ->
        reporteDeIncidente.cerrado() && reporteDeIncidente.equals(incidente)).toList().get(0).getFechaYhora(), incidente.getFechaYhora());

    //Básicamente lo anterior hace esto:
    /*for(ReporteDeIncidente reporteDeIncidente : incidentes){
      if (reporteDeIncidente.equals(incidente) && reporteDeIncidente.cerrado()){
        return ChronoUnit.HOURS.between(reporteDeIncidente.getFechaYhora(), incidente.getFechaYhora());
      }
    }*/
  }

  public boolean nuevo() {
    LocalDateTime fechaActual = LocalDateTime.now();
    Duration tiempoDesdeElReporte = Duration.between(fechaYhora,fechaActual);
    return tiempoDesdeElReporte.toHours() < 24;
  }

}
