package domain.Personas;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.Posicion;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.ReceptorDeNotificaciones;
import domain.Servicios.Servicio;
import domain.Usuario.Usuario;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import services.Localizacion.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class MiembroDeComunidad {
    private String apellido;
    private String nombre;
    private List<Entidad> entidadesDeInteres;
    private List<ParServicioRol> serviciosDeInteres;
    private List<Provincia> provincias;
    private List<Municipio> municipios;
    private Usuario usuario;
    private List<Comunidad> comunidades;
    private ReceptorDeNotificaciones receptorDeNotificaciones;

    public MiembroDeComunidad(String apellido, String nombre, String mail, String telefono) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.provincias = new ArrayList<>();
        this.municipios = new ArrayList<>();
        this.entidadesDeInteres = new ArrayList<>();
        this.serviciosDeInteres = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.receptorDeNotificaciones = new ReceptorDeNotificaciones(mail, telefono);
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

    public void agregarServicioDeInteres(Servicio servicio, Rol rol) {
        ParServicioRol parServicioRol = new ParServicioRol(servicio,rol);
        serviciosDeInteres.add(parServicioRol);
    }
    public void cambiarRolSobreServicio(Servicio servicio){
        Optional<ParServicioRol> parServicioRolExistente = this.serviciosDeInteres.stream().filter(parServicioRol -> parServicioRol.getServicio().equals(servicio)).findFirst();
        if(parServicioRolExistente.isPresent())
            parServicioRolExistente.get().cambiarRol();//de esta forma no creamos una nueva instancia sino que la modificamos
    }

    public void unirseAComunidad(Comunidad unaComunidad) {
        this.comunidades.add(unaComunidad);
        unaComunidad.agregarMiembro(this);
    }

    public boolean tieneInteres(Servicio servicio, Establecimiento establecimiento) {
        boolean coincideEstablecimiento = this.entidadesDeInteres.stream().anyMatch(entidad -> entidad.getEstablecimientos().contains(establecimiento));
        boolean coincideLocalizacion = this.municipios.stream().anyMatch(municipio -> establecimiento.getLocalizacion() == municipio);
        boolean coincideServicio = this.serviciosDeInteres.stream().anyMatch(servicioRol -> servicioRol.getServicio().equals(servicio));
        return coincideServicio && coincideEstablecimiento && coincideLocalizacion;
    }

    public void informarFuncionamiento(ReporteDeIncidente reporteDeIncidente) {//no nos importa donde se crea el reporte
        this.comunidades.forEach(comunidad -> comunidad.guardarIncidente(reporteDeIncidente));
    }

    public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente) {
        if (this.tieneInteres(reporteDeIncidente.getServicio(), reporteDeIncidente.getEstablecimiento())) {
            this.receptorDeNotificaciones.recibirNotificacion(reporteDeIncidente);
        }
    }

    public Posicion posicion(){
        //TOMOCK
        return null;
    }
    public boolean validarSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente){
        return reporteDeIncidente.getEstablecimiento().getPosicion().distancia(this.posicion()) <= Config.DISTANCIA_MINIMA
                && this.tieneInteres(reporteDeIncidente.getServicio(), reporteDeIncidente.getEstablecimiento());
    }
    public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
        if (this.validarSolicitudDeRevision(reporteDeIncidente)){
            this.receptorDeNotificaciones.recibirSolicitudDeRevision(reporteDeIncidente);
        }
    }
    public List<ReporteDeIncidente> solicitarInformacionDeIncidentesAbiertos(){
        List<ReporteDeIncidente> reportes = new ArrayList<>();
        this.comunidades.forEach(comunidad -> reportes.addAll(comunidad.getIncidentesDeLaComunidad()));
        return reportes.stream().filter(reporte -> reporte.getEstado().equals(EstadoIncidente.ABIERTO)).toList()
            .stream().collect(Collectors.toSet()).stream().toList(); //se usa el set para evitar repetidos, queda el último
    }
    public List<ReporteDeIncidente> solicitarInformacionDeIncidentesCerrado(){
        List<ReporteDeIncidente> reportes = new ArrayList<>();
        this.comunidades.forEach(comunidad -> reportes.addAll(comunidad.getIncidentesDeLaComunidad()));
        return reportes.stream().filter(reporte -> reporte.getEstado().equals(EstadoIncidente.CERRADO)).toList()
            .stream().collect(Collectors.toSet()).stream().toList(); //se repite entre las distintas comunidades el cierre
    }
}