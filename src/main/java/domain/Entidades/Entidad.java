package domain.Entidades;

import domain.Incidentes.ReporteDeIncidente;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "entidad")
public class Entidad {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String nombre;
    @OneToMany
    @JoinColumn(name="establecimiento_id",referencedColumnName = "id")
    private List<Establecimiento> establecimientos;
    @Enumerated(EnumType.STRING)
    private TipoEntidad tipoEntidad;

    public Entidad(String nombre, String tipo) {
        this.nombre = nombre;
        this.establecimientos = new ArrayList<>();
        this.tipoEntidad = TipoEntidad.valueOf(tipo);
    }
    public void agregarEstablecimiento(Establecimiento unEstablecimiento) {
        establecimientos.removeIf(establecimiento -> establecimiento.igualito(unEstablecimiento));
        establecimientos.add(unEstablecimiento);
    }

    public boolean igualito(Object obj) {
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

    public Integer numeroDeIncidentes(List<ReporteDeIncidente> incidentes){
        return incidentes.stream().filter(
            incidente->incidente.getEntidad().igualito(this)).toList().size();
    }
}
