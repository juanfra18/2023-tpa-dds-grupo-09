package domain.Personas;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Comunidad {
    private String nombre;
    private List<MiembroDeComunidad> miembros;

    public  Comunidad(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }
    public void agregarMiembro(MiembroDeComunidad unMiembro) {
        this.miembros.add(unMiembro);
    }
}