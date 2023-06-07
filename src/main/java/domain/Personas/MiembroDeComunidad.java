package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
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
    private List<Localizacion> localizaciones;
    private Usuario usuario;
    private List<Interes> intereses;
    private List<Comunidad> comunidades;


    public MiembroDeComunidad(String apellido, String nombre, String mail) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.mail = mail;
        this.localizaciones = new ArrayList<>();
        this.intereses = new ArrayList<>();
        this.entidadesDeInteres = new ArrayList<>();
        this.serviciosDeInteres = new ArrayList<>();
        this.comunidades = new ArrayList<>();
    }

    public void agregarLocalizacion(int id) throws IOException {
        localizaciones.add(Localizador.devolverLocalizacion(id));
    }

    public void agregarEntidadDeInteres(Entidad entidad) {
        entidadesDeInteres.add(entidad);
    }

    public void agregarServicioDeInteres(Servicio servicio) {
        serviciosDeInteres.add(servicio);
    }

    public void unirseAComunidad(Comunidad unaComunidad) {
        if (comparteInteres(unaComunidad)) {
            this.comunidades.add(unaComunidad);
            unaComunidad.agregarPersona(this);
        }
    }
    public boolean comparteInteres(Comunidad unaComunidad) {
        return intereses.contains(unaComunidad.getInteres());
    }

    public void agregarInteres() {
        for (Entidad entidad : this.entidadesDeInteres) {
            for (Establecimiento establecimiento : entidad.getEstablecimientos()) {
                for (Servicio servicio : establecimiento.getServicios()) {
                    if (meInteresa(this.localizaciones, servicio, establecimiento)) {
                        Interes interes = new Interes();
                        interes.setEntidad(entidad);
                        interes.setEstablecimiento(establecimiento);
                        interes.setServicio(servicio);
                        this.intereses.add(interes);
                    }
                }
            }
        }
    }

    private boolean meInteresa(List<Localizacion> localizaciones, Servicio servicio, Establecimiento establecimiento) {
        return (serviciosDeInteres.contains(servicio) && localizaciones.
            stream().anyMatch(localizacion -> localizacion.getId() == establecimiento.getLocalizacion().getId()));
    }
}