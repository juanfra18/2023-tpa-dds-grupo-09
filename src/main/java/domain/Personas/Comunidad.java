package domain.Personas;

import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.EmisorDeNotificaciones;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

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
                Incidente incidente = new Incidente(reporteDeIncidente.getEstablecimiento(), reporteDeIncidente.getServicio());
                incidente.agregarReporteDeApertura(reporteDeIncidente);
                this.incidentesDeLaComunidad.add(incidente);

                this.emisorDeNotificaciones.enviarNotificaciones(reporteDeIncidente, this.miembros); //se crea un nuevo incidente
            }
        }
    }
    public List<Incidente> incidentesAbiertos(){
        return this.incidentesDeLaComunidad.stream().filter(i -> !i.cerrado()).toList();
    }
}