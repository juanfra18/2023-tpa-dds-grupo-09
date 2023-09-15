package domain.Incidentes;

import domain.Entidades.Establecimiento;
import persistence.Persistente;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    @JoinColumn(name = "incidente_id",referencedColumnName = "id")
    private List<ReporteDeIncidente> reportes;

    public Incidente() {
        this.reportes = new ArrayList<>();
    }
    public List<ReporteDeIncidente> getReportesDeApertura(){
        return this.reportes.stream().filter(r -> !r.esDeCierre()).toList();
    }
    public List<ReporteDeIncidente> getReportesDeCierre(){
        return this.reportes.stream().filter(r -> r.esDeCierre()).toList();
    }
    public ReporteDeIncidente primeraApertura(){
        return this.getReportesDeApertura().get(0);
    }
    public ReporteDeIncidente primerCierre(){
        return this.getReportesDeCierre().get(0);
    }
    public void agregarReporteDeApertura(ReporteDeIncidente reporteDeIncidente){
        // Se asume que los reportes están en orden cronológico
        if (this.getReportesDeApertura().isEmpty()) {
            this.establecimiento = reporteDeIncidente.getEstablecimiento();
            this.servicio = reporteDeIncidente.getServicio();
        }
        this.reportes.add(reporteDeIncidente);
    }
    public void agregarReporteDeCierre(ReporteDeIncidente reporteDeIncidente){
        this.reportes.add(reporteDeIncidente);
    }

    public boolean cerrado(){
        return !(this.getReportesDeCierre().isEmpty());
    }
    public boolean tieneEstado(EstadoIncidente estadoIncidente){
        return (this.cerrado() && estadoIncidente == EstadoIncidente.CERRADO) || (!this.cerrado() && estadoIncidente == EstadoIncidente.ABIERTO);
    }
    public Long tiempoDeCierre(){
        return ChronoUnit.MINUTES.between(this.primeraApertura().getFechaYhora(),this.getReportesDeCierre().get(0).getFechaYhora());
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
        for(ReporteDeIncidente reporteDeIncidente: this.getReportesDeApertura())
        {   //se fija si hay reportes que ocurrieron luego de 24 horas y que hayan sido anteriores al primer cierre
            List<ReporteDeIncidente> reportesDeApertura = this.getReportesDeApertura().stream().filter(r -> !this.diferenciaMenor24Horas(r,reporteDeIncidente) && r.getFechaYhora().isBefore(this.primerCierre().getFechaYhora())).toList();
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