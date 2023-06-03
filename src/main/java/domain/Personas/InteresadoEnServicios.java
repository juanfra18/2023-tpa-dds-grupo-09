package domain.Personas;

import domain.Servicios.Servicio;
import domain.Servicios.EntidadPrestadora;
import domain.Usuario.Usuario;
import services.Localizacion.*;


import java.util.List;


public class InteresadoEnServicios {
    private String apellido;
    private String nombre;
    private String mail;
    private List<EntidadPrestadora> entidadesDeInteres;
    private List<Servicio> serviciosDeInteres;
    private List<Localizacion> localizaciones;
    private Usuario usuario;
    private GeneradorDeLocalizaciones generadorDeLocalizaciones = new GeneradorDeLocalizaciones();

    public void agregarLocalizacion(int idProvinciaSeleccionado, int idMunicipioSeleccionado, int idDepartamentoSeleccionado){
        localizaciones.add(generadorDeLocalizaciones.devolverLocalizacion(idProvinciaSeleccionado,idMunicipioSeleccionado,idDepartamentoSeleccionado));
    }
    public void agregarEntidadDeInteres(EntidadPrestadora entidad){
        entidadesDeInteres.add(entidad);
    }
    public void agregarServiciosDeInteres(Servicio servicio)
    {
        serviciosDeInteres.add(servicio);
    }
}