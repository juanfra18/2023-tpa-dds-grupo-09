package domain.Incidentes;

import domain.Entidades.EntidadPrestadora;
import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Incidente {
    private Establecimiento establecimiento;
    private Servicio servicio;
    private List<ReporteDeIncidente> reportesDeApertura;
    @Setter
    private ReporteDeIncidente reporteDeCierre;

    public Incidente(Establecimiento establecimiento, Servicio servicio) {
        this.establecimiento = establecimiento;
        this.servicio = servicio;
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

    public boolean igualito(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Incidente otro = (Incidente) obj;
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
