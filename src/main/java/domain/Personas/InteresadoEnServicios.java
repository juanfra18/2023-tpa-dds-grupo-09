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

    public void agregarLocalizacion(int idProvinciaSeleccionado, int idMunicipioSeleccionado, int idDepartamentoSeleccionado){
        if(idProvinciaSeleccionado != 0 && idMunicipioSeleccionado != 0 && idDepartamentoSeleccionado != 0)
        {
            Departamento departamentoSeleccionado = new Departamento(idDepartamentoSeleccionado);
            departamentoSeleccionado.setIdMunicipio(idMunicipioSeleccionado);
            departamentoSeleccionado.setIdProvincia(idProvinciaSeleccionado);

            localizaciones.add(departamentoSeleccionado);
        }
        else if(idProvinciaSeleccionado != 0 && idMunicipioSeleccionado != 0)
        {
            Municipio municipioSeleccionado = new Municipio(idMunicipioSeleccionado);
            municipioSeleccionado.setIdProvincia(idProvinciaSeleccionado);
            localizaciones.add(municipioSeleccionado);
        }
        else if (idProvinciaSeleccionado != 0)
        {
            Provincia provinciaSeleccionada = new Provincia(idProvinciaSeleccionado);
            localizaciones.add(provinciaSeleccionada);
        }
    }
    public void agregarEntidadDeInteres(EntidadPrestadora entidad){
        entidadesDeInteres.add(entidad);
    }
    public void agregarServiciosDeInteres(Servicio servicio)
    {
        serviciosDeInteres.add(servicio);
    }
}
