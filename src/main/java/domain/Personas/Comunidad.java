package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;

import domain.Incidentes.ReporteDeIncidente;

import domain.Notificaciones.EmisorDeNotificaciones;
import domain.Servicios.Servicio;
import lombok.Getter;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
public class Comunidad {
    private String nombre;
    private List<MiembroDeComunidad> miembros;
    private List<ReporteDeIncidente> incidentesDeLaComunidad;
    private EmisorDeNotificaciones emisorDeNotificaciones;

    public  Comunidad(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
        this.incidentesDeLaComunidad = new ArrayList<>();
        this.emisorDeNotificaciones = new EmisorDeNotificaciones(this);
    }
    public void agregarMiembro(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
    public void guardarIncidente(Entidad entidad, Establecimiento establecimiento, Servicio servicio,
                                 String estado, MiembroDeComunidad denunciante, String observaciones) {
        ReporteDeIncidente reporte = new ReporteDeIncidente(estado, Date.from(Instant.now()), Time.from(Instant.now()), denunciante, entidad, establecimiento, servicio, observaciones);
        this.incidentesDeLaComunidad.add(reporte);
        this.emisorDeNotificaciones.EnviarNotificaciones(reporte);
    }
}