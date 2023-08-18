package domain.Incidentes;

import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return ChronoUnit.HOURS.between(this.reporteDeCierre.getFechaYhora(), this.primeraApertura().getFechaYhora());
    }
}
