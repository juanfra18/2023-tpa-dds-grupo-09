package domain.Personas;

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
    private List<EntidadPrestadora> entidadesDeInteres;
    private List<Servicio> serviciosDeInteres;
    private List<Localizacion> localizaciones;
    private Usuario usuario;
    private Localizador localizador = new Localizador();


    public InteresadoEnServicios(String apellido, String nombre,String mail)  {
        this.apellido = apellido;
        this.nombre = nombre;
        this.mail = mail;
        localizaciones = new ArrayList<>();
    }
    public void agregarLocalizacion(int id) throws IOException {
        localizaciones.add(localizador.devolverLocalizacion(id));
    }
    public void agregarEntidadDeInteres(EntidadPrestadora entidad) {
        entidadesDeInteres.add(entidad);
    }
    public void agregarServiciosDeInteres(Servicio servicio) {
        serviciosDeInteres.add(servicio);
    }
    public List<Localizacion> getLocalizaciones() {
        return localizaciones;
    }
}