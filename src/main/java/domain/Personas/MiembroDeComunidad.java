package domain.Personas;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Incidentes.*;
import domain.Notificaciones.FormaDeNotificar;
import domain.Notificaciones.MedioDeComunicacion;
import domain.Notificaciones.ReceptorDeNotificaciones;
import domain.Persistencia.Persistente;
import domain.Servicios.Servicio;
import domain.Usuario.Usuario;
import lombok.Getter;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@Table(name="miembroDeComunidad")
public class MiembroDeComunidad extends Persistente {
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany
    @JoinColumn(name="entidad_id",referencedColumnName = "id")
    private List<Entidad> entidadesDeInteres;
    @OneToMany
    @JoinColumn(name = "par_servicio_rol_id", referencedColumnName = "id") //TODO checkear
    private List<ParServicioRol> serviciosDeInteres;
    @OneToMany
    @JoinColumn(name="provincia_id",referencedColumnName = "id")
    private List<Provincia> provincias;
    @OneToMany
    @JoinColumn(name="municipio_id",referencedColumnName = "id")
    private List<Municipio> municipios;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") // Asegura que la columna sea única
    private Usuario usuario;
    @ManyToMany
    private List<Comunidad> comunidades;
    @OneToOne
    private ReceptorDeNotificaciones receptorDeNotificaciones;
    @Transient
    private RepositorioDeIncidentes repositorioDeIncidentes;

    public MiembroDeComunidad(String apellido, String nombre, String mail, String telefono, FormaDeNotificar formaDeNotificarSeleccionada, MedioDeComunicacion medioDeComunicacionSeleccionado, RepositorioDeIncidentes repositorioDeIncidentes) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.provincias = new ArrayList<>();
        this.municipios = new ArrayList<>();
        this.entidadesDeInteres = new ArrayList<>();
        this.serviciosDeInteres = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.receptorDeNotificaciones = new ReceptorDeNotificaciones(medioDeComunicacionSeleccionado,formaDeNotificarSeleccionada,mail, telefono);
        this.repositorioDeIncidentes = repositorioDeIncidentes;
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

    public void informarFuncionamiento(ReporteDeIncidente reporteDeIncidente, Comunidad comunidad) {//no nos importa donde se crea el reporte
        repositorioDeIncidentes.registrarIncidente(reporteDeIncidente);
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
        List<Incidente> incidentesDeMisComunidades = new ArrayList<>();
        this.comunidades.forEach(comunidad -> incidentesDeMisComunidades.addAll(comunidad.getIncidentesDeLaComunidad()));

        List<Incidente> incidentesDeEstadoSeleccionado = new ArrayList<>();
        incidentesDeEstadoSeleccionado = incidentesDeMisComunidades.stream().filter(i -> i.tieneEstado(estado)).toList();

        List<Incidente> incidentesUnicos = new ArrayList<>();

        for(Incidente incidente : incidentesDeEstadoSeleccionado){
            if(!incidentesUnicos.stream().anyMatch(i -> i.igualito(incidente) && !i.equals(incidente)))
                incidentesUnicos.add(incidente);
        }
        return incidentesUnicos;
    }

    public List<Incidente> solicitarInformacionDeIncidentesAbiertos() {
        return obtenerIncidentesPorEstado(EstadoIncidente.ABIERTO);
    }

    public List<Incidente> solicitarInformacionDeIncidentesCerrados() {
        return obtenerIncidentesPorEstado(EstadoIncidente.CERRADO);
    }

}


