package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;

import java.util.ArrayList;
import java.util.List;

public class Interes {

    private List<Establecimiento> establecimientos;
    private List<Entidad> entidades;
    public Interes() {
        establecimientos = new ArrayList<>();
        entidades = new ArrayList<>();
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        establecimientos.add(establecimiento);
    }

    public void agregarEntidad(Entidad entidad) {
        entidades.add(entidad);
    }
}
