package domain.Personas;

import domain.Servicios.Servicio;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Comunidad {
    private String nombre;
    private List<MiembroDeComunidad> miembros;
    private List<Servicio> servicios;
    private Interes interes;

    public  Comunidad(String nombre, Interes interes) {
        this.nombre = nombre;
        this.interes = interes;
        this.miembros = new ArrayList<>();
    }
    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public void agregarPersona(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
}