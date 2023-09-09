package domain.Incidentes;

import domain.Entidades.Establecimiento;
import domain.Persistencia.Persistente;
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
    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "reporteDeIncidenteCierre_id",referencedColumnName = "id")
    private ReporteDeIncidente reporteDeCierre;

    public Incidente() {
        this.reportesDeApertura = new ArrayList<>();
    }

    public ReporteDeIncidente primeraApertura(){
        return this.reportesDeApertura.get(0);
    }
    public void agregarReporteDeApertura(ReporteDeIncidente ... reportes){
        // Se asume que los reportes están en orden cronológico
        Collections.addAll(this.reportesDeApertura, reportes);
    }

    public boolean cerrado(){
        return !(this.reporteDeCierre == null);
    }
    public boolean tieneEstado(EstadoIncidente estadoIncidente){
        return (this.cerrado() && estadoIncidente == EstadoIncidente.CERRADO)
                || (!this.cerrado() && estadoIncidente == EstadoIncidente.ABIERTO);
    }
    public Long tiempoDeCierre(){
        return ChronoUnit.MINUTES.between(this.primeraApertura().getFechaYhora(),this.reporteDeCierre.getFechaYhora());
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
