package domain.Personas;

import domain.Incidentes.ReporteDeIncidente;

import domain.Notificaciones.EmisorDeNotificaciones;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Comunidad {
    private String nombre;
    private List<MiembroDeComunidad> miembros;
    private List<ReporteDeIncidente> incidentesDeLaComunidad;
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
        this.incidentesDeLaComunidad.add(reporteDeIncidente);
        this.emisorDeNotificaciones.enviarNotificaciones(reporteDeIncidente, this.miembros);
    }

    public List<ReporteDeIncidente> incidentesAbiertos(){
        List<ReporteDeIncidente> incidentesDeCierre = incidentesDeLaComunidad.stream().filter(incidente -> incidente.cerrado()).toList();
        List<ReporteDeIncidente> incidentesDeApertura = incidentesDeLaComunidad.stream().filter(incidente -> !incidente.cerrado()).toList();

        List<ReporteDeIncidente> incidentesAbiertos = incidentesDeApertura.stream().filter(incidente ->
                                                 !incidentesDeCierre.stream().anyMatch(incidenteCierre ->
                                                  incidenteCierre.equals(incidente))).toList();
        return incidentesAbiertos;
    }
}