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
    private Servicio servicio;

}
