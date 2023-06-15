package domain.Entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Entidad {
    private String nombre;
    private List<Establecimiento> establecimientos;
    private TipoEntidad tipoEntidad;

    public Entidad(String nombre, String tipo) {
        this.nombre = nombre;
        this.establecimientos = new ArrayList<>();
        this.tipoEntidad = TipoEntidad.valueOf(tipo);
    }
    public void agregarEstablecimiento(Establecimiento unEstablecimiento) {
        establecimientos.removeIf(establecimiento -> establecimiento.equals(unEstablecimiento));
        establecimientos.add(unEstablecimiento);
    }
}
