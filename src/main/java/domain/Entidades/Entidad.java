package domain.Entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Entidad {
    private String nombre;
    private List<Establecimiento> establecimientos;
    private TipoEntidad tipoEntidad;
    private Integer CantidadDeIncidentesPorSemana = 0;

    public Entidad(String nombre, String tipo) {
        this.nombre = nombre;
        this.establecimientos = new ArrayList<>();
        this.tipoEntidad = TipoEntidad.valueOf(tipo);
    }
    public void agregarEstablecimiento(Establecimiento unEstablecimiento) {
        establecimientos.removeIf(establecimiento -> establecimiento.equals(unEstablecimiento));
        establecimientos.add(unEstablecimiento);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Entidad otro = (Entidad) obj;
        return Objects.equals(nombre, otro.nombre)
            && Objects.equals(tipoEntidad, otro.tipoEntidad);
    }

    public void nuevoIncidente() {
        this.CantidadDeIncidentesPorSemana++;
    }
}
