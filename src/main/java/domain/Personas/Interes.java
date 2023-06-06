package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import lombok.Getter;
import services.Localizacion.Localizacion;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Interes {
    private List<Entidad> entidades;
    private List<Establecimiento> establecimientos;
    private List<Localizacion> localizaciones;
    private List<Servicio> servicios;
    public Interes() {
        entidades = new ArrayList<>();
        establecimientos = new ArrayList<>();
        servicios = new ArrayList<>();
        localizaciones = new ArrayList<>();
    }

    public void agregarEntidad(Entidad entidad) {entidades.add(entidad);}
    public void agregarEstablecimiento(Establecimiento establecimiento) {
        establecimientos.add(establecimiento);
    }

    public void agregarServicios(List<Servicio> servicios) {
        servicios.addAll(servicios);
    }
    public  void agregarLocalizacion(Localizacion UnaLocalizacion) {
        localizaciones.add(UnaLocalizacion);
    }

}
