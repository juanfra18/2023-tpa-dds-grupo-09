package models.domain.Personas;

import models.domain.Incidentes.Incidente;
import models.domain.Incidentes.ReporteDeIncidente;
import models.domain.Notificaciones.EmisorDeNotificaciones;
import models.persistence.Persistente;
import models.persistence.Repositorios.RepositorioComunidad;
import lombok.Getter;
import lombok.Setter;
import models.persistence.Repositorios.RepositorioDeIncidentes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="comunidad")
public class Comunidad extends Persistente {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "gradosDeConfianza")
    private String gradosDeConfianza;
    @ManyToMany(mappedBy = "comunidades")
    private List<MiembroDeComunidad> miembros;
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "comunidad_id")
    private List<ReporteDeIncidente> reportesDeLaComunidad;
    @Transient
    private EmisorDeNotificaciones emisorDeNotificaciones;
    @Transient
    private RepositorioComunidad repositorioComunidad;
    @Transient
    private RepositorioDeIncidentes repositorioDeIncidentes;

    public Comunidad() {
        this.miembros = new ArrayList<>();
        this.reportesDeLaComunidad = new ArrayList<>();
        //this.repositorioComunidad = RepositorioComunidad.getInstancia();
        //this.repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    }
    public void agregarMiembro(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
    public boolean cerroIncidente(Incidente incidente) {
        return this.reportesDeLaComunidad.stream().anyMatch(r -> incidente.getReportesDeCierre().contains(r));
    }
    public boolean abrioIncidente(Incidente incidente) {
        return this.reportesDeLaComunidad.stream().anyMatch(r -> incidente.getReportesDeApertura().contains(r));
    }
    public List<Incidente> getIncidentesDeComunidad(List<Incidente> incidentes) {
        return incidentes.stream().filter(i -> this.incidenteEsDeComunidad(i)).toList();
    }
    public boolean incidenteEsDeComunidad(Incidente incidente) {
        return this.reportesDeLaComunidad.stream().anyMatch(r -> incidente.getReportesDeApertura().contains(r));
    }
    public void guardarIncidente(ReporteDeIncidente reporteDeIncidente) {
        repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
        List<Incidente> incidentes = repositorioDeIncidentes.buscarTodos();
        List<Incidente> incidentesSobreLaMismaProblematica = incidentes.stream().filter(i -> i.getEstablecimiento().igualito(reporteDeIncidente.getEstablecimiento()) && i.getServicio().igualito(reporteDeIncidente.getServicio())).toList();

        if (incidentesSobreLaMismaProblematica.isEmpty()) //va a ser siempre de apertura
        {
            Incidente incidente = new Incidente();
            incidente.agregarReporteDeApertura(reporteDeIncidente);
            repositorioDeIncidentes.agregar(incidente);
        } else {
            boolean agregado = false;
            for (Incidente incidente : incidentesSobreLaMismaProblematica) {
                if (this.incidenteEsDeComunidad(incidente) && !agregado && !this.cerroIncidente(incidente)) {
                    if(reporteDeIncidente.esDeCierre())
                    {
                        incidente.agregarReporteDeCierre(reporteDeIncidente);
                        agregado = true;
                    }
                    else if(!reporteDeIncidente.esDeCierre())
                    {
                        incidente.agregarReporteDeApertura(reporteDeIncidente); //lo agrego, va a haber mas de un reporte de apertura de esta comunidad
                        agregado = true;
                    }
                }
                else if(!this.incidenteEsDeComunidad(incidente) && !agregado) //primer incidente no abierto por la comunidad
                {
                    incidente.agregarReporteDeApertura(reporteDeIncidente);
                    agregado = true;
                }
            }
            if (!agregado) { //en principio siempre acá es de apertura
                Incidente incidente = new Incidente();
                incidente.agregarReporteDeApertura(reporteDeIncidente);
                repositorioDeIncidentes.agregar(incidente);
            }
        }
        this.reportesDeLaComunidad.add(reporteDeIncidente);
        repositorioComunidad = RepositorioComunidad.getInstancia();
        repositorioComunidad.agregar(this);
    }
    public List<Incidente> incidentesAbiertos(List<Incidente> incidentes){
        return this.getIncidentesDeComunidad(incidentes).stream().filter(i -> !i.cerrado()).toList();
    }

    public int cantidadMiembros(){
        return  this.miembros.size();
    }
}