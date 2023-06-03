package domain.Servicios;

import lombok.Getter;
import services.Localizacion.Localizacion;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Entidad {
    private String nombre;
    private List<Establecimiento> establecimientos;
    private TipoEntidad tipoEntidad;
    private Localizacion localizacion;

    public Entidad(String nombre, String tipoEntidad){
        this.nombre = nombre;
        this.tipoEntidad = TipoEntidad.valueOf(tipoEntidad);
        this.establecimientos = new ArrayList<>();
    }

    public void agregarEstablecimiento(Establecimiento establecimiento){establecimientos.add(establecimiento);}

}
