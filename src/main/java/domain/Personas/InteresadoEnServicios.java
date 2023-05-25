package domain.Personas;

import domain.Servicios.Servicio;
import services.Localizacion.Localizacion;
import domain.Servicios.Entidad;
import domain.Usuario.Usuario;

import java.util.List;

public class InteresadoEnServicios {
    private String apellido;
    private String nombre;
    private String mail;
    private List<Entidad> entidadesDeInteres;
    private List<Servicio> serviciosDeInteres;
    private List<Localizacion> localizaciones;
    private Usuario usuario;
    public void agregarLocalizacion(int id){
        //TODO
    }
}
