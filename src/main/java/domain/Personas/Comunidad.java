package domain.Personas;

import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;

import domain.Notificaciones.EmisorDeNotificaciones;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Comunidad {
    private String nombre;
    private List<MiembroDeComunidad> miembros;
    private List<Incidente> incidentesDeLaComunidad;
    private EmisorDeNotificaciones emisorDeNotificaciones;

    public Comunidad(String nombre, EmisorDeNotificaciones emisorDeNotificaciones) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
        this.incidentesDeLaComunidad = new ArrayList<>();
        this.emisorDeNotificaciones = emisorDeNotificaciones;
    }
    public void agregarMiembro(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
    public void guardarIncidente(ReporteDeIncidente reporteDeIncidente) {
        Optional<Incidente> incidenteOptional = this.incidentesDeLaComunidad.stream().filter(i -> i.getEstablecimiento().igualito(reporteDeIncidente.getEstablecimiento()) && i.getServicio().equals(reporteDeIncidente.getServicio())).findFirst();
        if (incidenteOptional.isPresent()) {
            if (reporteDeIncidente.esDeCierre()){
                incidenteOptional.get().setReporteDeCierre(reporteDeIncidente);
            }
            else {
                incidenteOptional.get().agregarReporteDeApertura(reporteDeIncidente);
            }
        }
        else {
            Incidente incidente = new Incidente(reporteDeIncidente.getEstablecimiento(), reporteDeIncidente.getServicio());
            incidente.agregarReporteDeApertura(reporteDeIncidente);
            this.incidentesDeLaComunidad.add(incidente);
        }
        this.emisorDeNotificaciones.enviarNotificaciones(reporteDeIncidente, this.miembros);
    }
    public List<Incidente> incidentesAbiertos(){
        return this.incidentesDeLaComunidad.stream().filter(i -> !i.cerrado()).toList();
    }
}