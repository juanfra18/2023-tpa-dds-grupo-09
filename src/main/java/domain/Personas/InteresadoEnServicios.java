package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import domain.Entidades.EntidadPrestadora;
import domain.Usuario.Usuario;
import services.Localizacion.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InteresadoEnServicios {
    private String apellido;
    private String nombre;
    private String mail;
    private List<Entidad> entidadesDeInteres; // Decia entidad prestadora, después borrar import
    private List<Servicio> serviciosDeInteres;
    private List<Localizacion> localizaciones;
    private Usuario usuario;
    private Localizador localizador = new Localizador();
    private List<Interes> intereses;
    private Comunidad comunidad;


    public InteresadoEnServicios(String apellido, String nombre, String mail) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.mail = mail;
        localizaciones = new ArrayList<>();
        intereses = new ArrayList<>();
    }

    public void agregarLocalizacion(int id) throws IOException {
        localizaciones.add(localizador.devolverLocalizacion(id));
    }

    public void agregarEntidadDeInteres(Entidad entidad) {
        entidadesDeInteres.add(entidadesDeInteres);
    }

    public void agregarServiciosDeInteres(Servicio servicio) {
        serviciosDeInteres.add(servicio);
    }

    public List<Localizacion> getLocalizaciones() {
        return localizaciones;
    }¿

    public void unirseAComunidad(Comunidad unaComunidad) {
        if (compartesInteres(unaComunidad)) {
            comunidad = unaComunidad;
        }
    }
    public boolean compartesInteres(Comunidad unaComunidad) {
        intereses.stream().anyMatch(interes -> interes.equals(unaComunidad.getInteres()));
    }
    public void interes(List<Entidad> entidadesDeInteres, List<Localizacion> localizaciones,  List<Servicio> serviciosDeInteres) {

        for (Entidad entidad : entidadesDeInteres) {
            for (Establecimiento establecimiento : entidad.getEstablecimientos()) {
                if (meInteresa(localizaciones, serviciosDeInteres, establecimiento)) {
                   Interes interes = new Interes();
                   interes.agregarEstablecimiento(establecimiento);
                   interes.agregarEntidad(entidad);
                   intereses.add(interes);
                }
            }
        }
    }

    private boolean meInteresa(List<Localizacion> localizaciones, List<Servicio> serviciosDeInteres, Establecimiento establecimiento) {
        return localizaciones.contains(establecimiento.getLocalizacion()) &&
                establecimiento.establecimientoContieneServicios(establecimiento, serviciosDeInteres);
    }
}