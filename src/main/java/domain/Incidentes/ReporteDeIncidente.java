package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import lombok.Getter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@Getter
public class ReporteDeIncidente {
  private final EstadoIncidente clasificacion;
  private final LocalDateTime fechaYhora;
  private final MiembroDeComunidad denunciante;
  private final Establecimiento establecimiento;
  private final Servicio servicio;
  private final String observaciones;
  private final Entidad entidad;

  public ReporteDeIncidente(String estado, LocalDateTime fechaYhora, MiembroDeComunidad denunciante, Entidad entidad,
                            Establecimiento establecimiento, Servicio servicio, String observaciones) {
    this.clasificacion = EstadoIncidente.valueOf(estado);
    this.fechaYhora = fechaYhora;
    this.denunciante = denunciante;
    this.establecimiento = establecimiento;
    this.servicio = servicio;
    this.observaciones = observaciones;
    this.entidad = entidad;
  }
  public boolean igualito(Object obj) {
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

  public Boolean esDeCierre(){
    return clasificacion == EstadoIncidente.CERRADO;
  }

  public String mensaje() {
    return "Incidente en "+ this.getEstablecimiento().getNombre()+", " +
        "en el servicio "+ this.getServicio().getTipo()+"." +
        "\nObservaciones: "+this.getObservaciones();
  }

  public boolean dentroDeEstaSemana() {
    LocalDateTime inicioDeSemana = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDateTime finalDeSemana = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

    return ((this.getFechaYhora().isAfter(inicioDeSemana) && this.getFechaYhora().isBefore(finalDeSemana)
              || this.esElMismoDia(inicioDeSemana) || this.esElMismoDia(finalDeSemana)));
  }

  /*public Long tiempoDeCierre(ReporteDeIncidente incidente, List<ReporteDeIncidente> incidentes) {
    //asume que ya le llega la lista con 1 reporte de apertura y 1 de cierre por incidente
    return ChronoUnit.HOURS.between(incidentes.stream().filter(reporteDeIncidente ->
        reporteDeIncidente.cerrado() && reporteDeIncidente.equals(incidente)).toList().get(0).getFechaYhora(), incidente.getFechaYhora());
    }
  }*/

  /*
  public boolean nuevo() {
    LocalDateTime fechaActual = LocalDateTime.now();
    Duration tiempoDesdeElReporte = Duration.between(fechaYhora,fechaActual);
    return tiempoDesdeElReporte.toHours() < 24;
  }*/

  public boolean esElMismoDia(LocalDateTime lunesOdomingo){ //si no esta entre semana, es el lunes o domingo de esta semana
    return lunesOdomingo.getDayOfMonth() == this.fechaYhora.getDayOfMonth() &&
        lunesOdomingo.getMonth() == this.fechaYhora.getMonth() &&
        lunesOdomingo.getYear() == this.fechaYhora.getYear();
  }

}
