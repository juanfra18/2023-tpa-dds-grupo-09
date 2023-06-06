package domain.Personas;

import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import services.Localizacion.Localizacion;

import java.util.ArrayList;
import java.util.List;

public class Interes {

    private List<Establecimiento> establecimientos;
    private List<Localizacion> localizaciones;
    private List<Servicio> servicios;
    public Interes() {
        establecimientos = new ArrayList<>();
        servicios = new ArrayList<>();
        localizaciones = new ArrayList<>();
    }

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
