package domain.Personas;

import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParServicioRol {
    private Servicio servicio;
    private Rol rol;

    public ParServicioRol(Servicio servicio, Rol rol) {
        this.servicio = servicio;
        this.rol = rol;
    }
}
