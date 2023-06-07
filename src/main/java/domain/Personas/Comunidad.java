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

    public void unirseAComunidad(MiembroDeComunidad unMiembro) {
        if (comparteInteres(unMiembro)) {
            this.miembros.add(unMiembro);
        }
    }
    public boolean comparteInteres(MiembroDeComunidad unMiembro) {
        return interes.equals(unMiembro.getIntereses());
    }
}