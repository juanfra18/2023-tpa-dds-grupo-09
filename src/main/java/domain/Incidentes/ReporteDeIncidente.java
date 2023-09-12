package domain.Incidentes;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import persistence.Converters.LocalDateTimeAttributeConverter;
import persistence.Persistente;
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
  @Column(name = "clasificacion")
  private EstadoIncidente clasificacion;
  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @Column(name = "fechaYHora")
  private LocalDateTime fechaYhora;
  @ManyToOne
  @JoinColumn(name="miembro_id",referencedColumnName = "id")
  private MiembroDeComunidad denunciante;
  @ManyToOne
  @JoinColumn(name="establecimiento_id",referencedColumnName = "id")
  private Establecimiento establecimiento;
  //@ManyToOne
  //@JoinColumn(name = "servicio_id", referencedColumnName = "id")
  @Transient
  private Servicio servicio;
  @Column(name = "observaciones", columnDefinition = "text")
  private String observaciones;
  @ManyToOne
  @JoinColumn(name="entidad_id",referencedColumnName = "id")
  private Entidad entidad;

  public ReporteDeIncidente() {}
  public String getNombre() {
    return String.valueOf(establecimiento) + " " +  String.valueOf(servicio);
  }
  public boolean igualito(ReporteDeIncidente reporteDeIncidente) {
    if (this == reporteDeIncidente) {
      return true;
    }
    if (reporteDeIncidente == null || getClass() != reporteDeIncidente.getClass()) {
      return false;
    }
    ReporteDeIncidente otro = reporteDeIncidente;
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
