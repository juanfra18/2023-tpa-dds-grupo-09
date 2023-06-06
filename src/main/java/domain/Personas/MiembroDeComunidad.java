package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import domain.Usuario.Usuario;
import services.Localizacion.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        localizaciones = new ArrayList<>();
        intereses = new ArrayList<>();
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

    public List<Localizacion> getLocalizaciones() {
        return localizaciones;
    }

    public void unirseAComunidad(Comunidad unaComunidad) {
        if (comparteInteres(unaComunidad)) {
            this.comunidades.add(unaComunidad);
        }
    }
    public boolean comparteInteres(Comunidad unaComunidad) {
        return intereses.stream().anyMatch(interes -> interes.equals(unaComunidad.getInteres()));
    }
    public void interes(List<Entidad> entidadesDeInteres, List<Localizacion> localizaciones,  List<Servicio> serviciosDeInteres) {

        for (Entidad entidad : entidadesDeInteres) {
            for (Establecimiento establecimiento : entidad.getEstablecimientos()) {
                if (meInteresa(localizaciones, serviciosDeInteres, establecimiento)) {
                    Interes interes = new Interes();
                    interes.agregarEstablecimiento(establecimiento);
                    interes.agregarServicios(serviciosDeInteres);
                    interes.agregarLocalizacion((establecimiento.getLocalizacion()));
                    intereses.add(interes);
                }
            }
        }
    }

    private boolean meInteresa(List<Localizacion> localizaciones, List<Servicio> serviciosDeInteres, Establecimiento establecimiento) {
        for(Servicio servicio : serviciosDeInteres) {
            if(establecimiento.establecimientoContieneServicio(servicio) && localizaciones.contains(establecimiento.getLocalizacion())){
                return  true;
            }
            return  false;
        }
        return false;
    }
}