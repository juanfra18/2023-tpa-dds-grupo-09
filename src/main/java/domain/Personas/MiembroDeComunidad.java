package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.ReceptorDeNotificaciones;
import domain.Servicios.Servicio;
import domain.Usuario.Usuario;
import lombok.Getter;
import services.Localizacion.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MiembroDeComunidad {
    private String apellido;
    private String nombre;
    private String mail;
    private List<Entidad> entidadesDeInteres;
    private List<Servicio> serviciosDeInteres;
    private List<Provincia> provincias;
    private List<Municipio> municipios;
    private Usuario usuario;
    private List<Comunidad> comunidades;
    private ReceptorDeNotificaciones receptorDeNotificaciones;


    public MiembroDeComunidad(String apellido, String nombre, String mail) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.mail = mail;
        this.provincias = new ArrayList<>();
        this.municipios = new ArrayList<>();
        this.entidadesDeInteres = new ArrayList<>();
        this.serviciosDeInteres = new ArrayList<>();
        this.comunidades = new ArrayList<>();
    }

    public void agregarProvincia(Provincia provincia) {
        provincias.add(provincia);
    }

    public void agregarMunicipio(Municipio municipio) {
        municipios.add(municipio);
    }
    public void agregarEntidadDeInteres(Entidad entidad) {
        entidadesDeInteres.add(entidad);
    }

    public void agregarServicioDeInteres(Servicio servicio) {
        serviciosDeInteres.add(servicio);
    }

    public void unirseAComunidad(Comunidad unaComunidad) {
        this.comunidades.add(unaComunidad);
        unaComunidad.agregarMiembro(this);
    }

    public boolean tieneInteres(Servicio servicio, Establecimiento establecimiento) {

        boolean coincideEstablecimiento = this.entidadesDeInteres.stream().anyMatch(entidad -> entidad.getEstablecimientos().contains(establecimiento));
        boolean coincideLocalizacion = this.municipios.stream().anyMatch(municipio -> establecimiento.getLocalizacion() == municipio);
        boolean coincideServicio = this.serviciosDeInteres.contains(servicio);

        return coincideServicio && coincideEstablecimiento && coincideLocalizacion;
    }

    public void informarFuncionamiento(Entidad entidad, Establecimiento establecimiento, Servicio servicio, String estado, String observaciones) {
        this.comunidades.forEach(comunidad -> comunidad.guardarIncidente(entidad, establecimiento, servicio, estado, this, observaciones));
    }

    public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente) {
        this.receptorDeNotificaciones.recibirNotificacion(reporteDeIncidente,this);
    }

    public int distancia(ReporteDeIncidente reporteDeIncidente) {
        //TODO
        return 0;
    }
}