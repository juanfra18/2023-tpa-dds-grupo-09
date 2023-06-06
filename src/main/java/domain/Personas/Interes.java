package domain.Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;
import services.Localizacion.Localizacion;

@Getter
@Setter
public class Interes {
    private Entidad entidad;
    private Establecimiento establecimiento;
    private Localizacion localizacion;
    private Servicio servicio;

    public boolean equals(Interes interes){
        return entidad.getNombre().equals(interes.getEntidad().getNombre()) &&
            establecimiento.getNombre().equals(interes.getEstablecimiento().getNombre()) &&
            localizacion.getId() == interes.getLocalizacion().getId() &&
            servicio.getTipo().equals(interes.getServicio().getTipo());
    }

}
