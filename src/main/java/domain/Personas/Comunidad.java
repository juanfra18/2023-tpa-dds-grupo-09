package domain.Personas;

import domain.Servicios.Servicio;
import lombok.Getter;

import java.util.List;
@Getter
public class Comunidad {
    private List<MiembroDeComunidad> miembros;
    private List<Servicio> servicios;
    private Interes interes;
    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }
}
