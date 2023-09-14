package domain.Personas;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Incidentes.*;
import domain.Notificaciones.ReceptorDeNotificaciones;
import persistence.Persistente;
import persistence.Repositorios.RepositorioDeIncidentes;
import persistence.Repositorios.RepositorioDeReportesDeIncidentes;
import domain.Servicios.Servicio;
import domain.Usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
import persistence.Repositorios.RepositorioEntidad;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="miembroDeComunidad")
public class MiembroDeComunidad extends Persistente {
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany
    private List<Entidad> entidadesDeInteres;
    //@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}) //REVISAR ESTA RELACION TODO
    //@JoinColumn(name = "par_servicio_rol_id", referencedColumnName = "id") //TODO checkear
    @Transient
    private List<ParServicioRol> serviciosDeInteres;
    @ManyToMany
    private List<Provincia> provincias;
    @ManyToMany
    private List<Municipio> municipios;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") // Asegura que la columna sea única
    private Usuario usuario;
    @ManyToMany
    private List<Comunidad> comunidades;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private ReceptorDeNotificaciones receptorDeNotificaciones;
    @Transient
    private RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes;

    public MiembroDeComunidad() {
        this.provincias = new ArrayList<>();
        this.municipios = new ArrayList<>();
        this.entidadesDeInteres = new ArrayList<>();
        this.serviciosDeInteres = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.receptorDeNotificaciones = new ReceptorDeNotificaciones(); //para que se persiste automaticamente
       // this.repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
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
        ParServicioRol parServicioRol = new ParServicioRol();
        parServicioRol.setServicio(servicio);
        parServicioRol.setRol(rol);
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

    public void informarFuncionamiento(ReporteDeIncidente reporteDeIncidente, Comunidad comunidad) {//no nos importa donde se crea el reporte
        repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
        repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente);
        comunidad.guardarIncidente(reporteDeIncidente);
    }

    public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente) {
        if (this.tieneInteres(reporteDeIncidente.getServicio(), reporteDeIncidente.getEstablecimiento())) {
            this.receptorDeNotificaciones.recibirNotificacion(reporteDeIncidente);
        }
    }

    public Posicion setPosicion(){
        //TOMOCK
        return null;
    }
    public boolean validarSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente){
        return reporteDeIncidente.getEstablecimiento().getPosicion().distancia(this.setPosicion()) <= Config.DISTANCIA_MINIMA
            && this.tieneInteres(reporteDeIncidente.getServicio(), reporteDeIncidente.getEstablecimiento());
    }
    public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
        if (this.validarSolicitudDeRevision(reporteDeIncidente)){
            this.receptorDeNotificaciones.recibirSolicitudDeRevision(reporteDeIncidente);
        }
    }
    public List<Incidente> obtenerIncidentesPorEstado(EstadoIncidente estado) {
        RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
        List<Incidente> incidentes = repositorioDeIncidentes.buscarTodos();

        //Se queda con los incidentes que pertenezcan por lo menos a una de sus comunidades
        //Estos incidentes no estaran repetidos, seran unicos
        List<Incidente> incidentesDeMisComunidades = incidentes.stream().filter(incidente -> this.comunidades.stream().anyMatch(comunidad -> comunidad.incidenteEsDeComunidad(incidente))).toList();

        List<Incidente> incidentesDeEstadoSeleccionado = incidentesDeMisComunidades.stream().filter(i -> i.tieneEstado(estado)).toList();

        System.out.println(incidentesDeMisComunidades.size());
        System.out.println(incidentesDeEstadoSeleccionado.size());
        return incidentesDeEstadoSeleccionado;
    }

    public List<Incidente> solicitarInformacionDeIncidentesAbiertos() {
        return obtenerIncidentesPorEstado(EstadoIncidente.ABIERTO);
    }

    public List<Incidente> solicitarInformacionDeIncidentesCerrados() {
        return obtenerIncidentesPorEstado(EstadoIncidente.CERRADO);
    }

    public boolean afectadoPor(Incidente incidente) {
        boolean tieneRolAfectado = this.serviciosDeInteres.stream().anyMatch(servicioRol -> servicioRol.getServicio().igualito(incidente.getServicio()));
        return this.tieneInteres(incidente.getServicio(), incidente.getEstablecimiento()) && tieneRolAfectado;
    }
}


