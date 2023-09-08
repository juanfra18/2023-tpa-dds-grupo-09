package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Persistencia.Converters.LocalDateTimeAttributeConverter;
import domain.Persistencia.Persistente;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name="reporteDeIncidente")
public class ReporteDeIncidente extends Persistente {
  @Enumerated(EnumType.STRING)
  private  EstadoIncidente clasificacion;
  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @Column(name = "fechaYHora")
  private  LocalDateTime fechaYhora;
  @ManyToOne
  @JoinColumn(name="miembro_id",referencedColumnName = "id")
  private  MiembroDeComunidad denunciante;
  @ManyToOne
  @JoinColumn(name="establecimiento_id",referencedColumnName = "id")
  private  Establecimiento establecimiento;
  @ManyToOne
  @JoinColumn(name = "servicio_id", referencedColumnName = "id")
  private  Servicio servicio;
  @Column(name = "observaciones", columnDefinition = "text")
  private  String observaciones;
  @ManyToOne
  @JoinColumn(name="entidad_id",referencedColumnName = "id")
  private  Entidad entidad;

  public ReporteDeIncidente() {
    /*
    String estado, LocalDateTime fechaYhora, MiembroDeComunidad denunciante, Entidad entidad,
                               Establecimiento establecimiento, Servicio servicio, String observaciones
    this.clasificacion = EstadoIncidente.valueOf(estado);
    this.fechaYhora = fechaYhora;
    this.denunciante = denunciante;
    this.establecimiento = establecimiento;
    this.servicio = servicio;
    this.observaciones = observaciones;
    this.entidad = entidad;
    */
  }

  public String getNombre() {
    return String.valueOf(establecimiento) + " " +  String.valueOf(servicio);
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

  public String mensaje() {
    return "Incidente en "+ this.getEstablecimiento().getNombre()+", " +
        "en el servicio "+ this.getServicio().getTipo()+"." +
        "\nObservaciones: "+this.getObservaciones();
  }

}
