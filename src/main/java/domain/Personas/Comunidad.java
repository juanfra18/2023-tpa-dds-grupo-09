package domain.Personas;

import ServicioAPI.RepoComunidad;
import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.EmisorDeNotificaciones;
import domain.Persistencia.Persistente;
import domain.Persistencia.Repositorios.RepositorioComunidad;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToMany(mappedBy = "comunidades")
    private List<MiembroDeComunidad> miembros;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Incidente> incidentesDeLaComunidad;
    @Transient
    private EmisorDeNotificaciones emisorDeNotificaciones;
    @Transient
    private RepositorioComunidad repositorioComunidad;

    public Comunidad() {
        this.miembros = new ArrayList<>();
        this.incidentesDeLaComunidad = new ArrayList<>();
    }
    public void agregarMiembro(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
    public void guardarIncidente(ReporteDeIncidente reporteDeIncidente) {
        List<Incidente> incidentesSobreLaMismaProblematica = this.incidentesDeLaComunidad.stream().filter(i -> i.getEstablecimiento().igualito(reporteDeIncidente.getEstablecimiento()) && i.getServicio().igualito(reporteDeIncidente.getServicio())).toList();
        Incidente mismoIncidente = null;

        if(!incidentesSobreLaMismaProblematica.isEmpty())
        {
            mismoIncidente  = incidentesSobreLaMismaProblematica.get(incidentesSobreLaMismaProblematica.size()-1);
        }
        //para obtener el mismo incidente pero el mas reciente

        if (mismoIncidente != null && !mismoIncidente.cerrado()) {
            if (reporteDeIncidente.esDeCierre()){
                mismoIncidente.setReporteDeCierre(reporteDeIncidente);
                this.emisorDeNotificaciones.enviarNotificaciones(reporteDeIncidente, this.miembros); //se cierra incidente
            }
            else {
                mismoIncidente.agregarReporteDeApertura(reporteDeIncidente);
            }
        }
        else { //si no existe o fue cerrado, creo uno nuevo
            if(!reporteDeIncidente.esDeCierre())
            {
                Incidente incidente = new Incidente();
                incidente.setEstablecimiento(reporteDeIncidente.getEstablecimiento());
                incidente.setServicio(reporteDeIncidente.getServicio());
                incidente.agregarReporteDeApertura(reporteDeIncidente);
                this.incidentesDeLaComunidad.add(incidente);

                this.emisorDeNotificaciones.enviarNotificaciones(reporteDeIncidente, this.miembros); //se crea un nuevo incidente
            }
        }
        repositorioComunidad.modificar(this);
    }
    public List<Incidente> incidentesAbiertos(){
        return this.incidentesDeLaComunidad.stream().filter(i -> !i.cerrado()).toList();
    }
}