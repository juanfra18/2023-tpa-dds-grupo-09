package domain.Entidades;

import domain.Servicios.Servicio;
import lombok.Getter;
import services.Localizacion.Localizacion;
import services.Localizacion.Localizador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Getter
public abstract class Entidad {
    protected String nombre;
    protected List<Establecimiento> establecimientos;
    protected Localizacion localizacion;

    protected Entidad(String nombre, int idLocalizacion) throws IOException {
        this.nombre = nombre;
        this.establecimientos = new ArrayList<>();
        this.localizacion = Localizador.devolverLocalizacion(idLocalizacion);
    }
    public void agregarEstablecimiento(Establecimiento establecimiento) throws IOException {
        establecimientos.add(establecimiento);
    }
    public void asignarLocalizacion(int id) throws IOException {
        this.localizacion = Localizador.devolverLocalizacion(id);
    }
}
