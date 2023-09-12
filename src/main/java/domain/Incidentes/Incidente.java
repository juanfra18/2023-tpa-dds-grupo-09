package domain.Incidentes;

import domain.Entidades.Establecimiento;
import persistence.Persistente;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="incidente")
public class Incidente extends Persistente {
    @ManyToOne
    @JoinColumn(name="establecimiento_id",referencedColumnName = "id")
    private Establecimiento establecimiento;
    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "incidente_id")
    private List<ReporteDeIncidente> reportesDeApertura;
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "incidente_id",referencedColumnName = "id")
    private List<ReporteDeIncidente> reportesDeCierre;

    public Incidente() {
        this.reportesDeApertura = new ArrayList<>();
        this.reportesDeCierre = new ArrayList<>();
    }

    public ReporteDeIncidente primeraApertura(){
        return this.reportesDeApertura.get(0);
    }
    public void agregarReporteDeApertura(ReporteDeIncidente reporteDeIncidente){
        // Se asume que los reportes están en orden cronológico
        if (this.reportesDeApertura.isEmpty()) {
            this.establecimiento = reporteDeIncidente.getEstablecimiento();
            this.servicio = reporteDeIncidente.getServicio();
        }
        this.reportesDeApertura.add(reporteDeIncidente);
    }
    public void agregarReporteDeCierre(ReporteDeIncidente reporteDeIncidente){
        this.reportesDeCierre.add(reporteDeIncidente);
    }

    public boolean cerrado(){
        return !(this.reportesDeCierre.isEmpty());
    }
    public boolean tieneEstado(EstadoIncidente estadoIncidente){
        return (this.cerrado() && estadoIncidente == EstadoIncidente.CERRADO)
                || (!this.cerrado() && estadoIncidente == EstadoIncidente.ABIERTO);
    }
    public Long tiempoDeCierre(){
        return ChronoUnit.MINUTES.between(this.primeraApertura().getFechaYhora(),this.reportesDeCierre.get(0).getFechaYhora());
    } //lo hacemos en minutos y dsp se pasa a horas y minutos en el ranking

    public boolean igualito(Incidente incidente) {
        if (this == incidente) {
            return true;
        }
        if (incidente == null || getClass() != incidente.getClass()) {
            return false;
        }
        Incidente otro = incidente;
        return Objects.equals(establecimiento, otro.getEstablecimiento())
            && Objects.equals(servicio,otro.getServicio());
    }

    public Integer diasAbierto(){
        int dias = 1; //de base ya estuvo abierto ese mismo dia
        for(ReporteDeIncidente reporteDeIncidente: reportesDeApertura)
        {
          reportesDeApertura = reportesDeApertura.stream().filter(r -> !this.diferenciaMenor24Horas(r,reporteDeIncidente)).toList();
          if(!reportesDeApertura.isEmpty()) dias++;
        }
        return dias;
    }

    private boolean diferenciaMenor24Horas(ReporteDeIncidente reporteDeIncidente1, ReporteDeIncidente reporteDeIncidente2){
        return Math.abs(ChronoUnit.HOURS.between(reporteDeIncidente1.getFechaYhora(), reporteDeIncidente2.getFechaYhora()))<24;
    }

    public String mensaje(){
        return primeraApertura().mensaje();
    }
}
