package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.ReporteDeIncidente;
import domain.Incidentes.RepositorioDeIncidentes;
import domain.Servicios.Servicio;
import lombok.Getter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
@Getter
public class Comunidad {
    private String nombre;
    private List<MiembroDeComunidad> miembros;
    private RepositorioDeIncidentes incidentes;

    public  Comunidad(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
        this.incidentes = new RepositorioDeIncidentes();
    }
    public void agregarMiembro(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
    public void guardarIncidente(Servicio servicio, Entidad entidad, Establecimiento establecimiento, EstadoIncidente estado) {
        ReporteDeIncidente reporte = new ReporteDeIncidente(estado, Time.valueOf());
    }
}