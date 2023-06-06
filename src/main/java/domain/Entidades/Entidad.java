package domain.Entidades;

import domain.Servicios.Servicio;
import lombok.Getter;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.ListadoDeMunicipios;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Localizacion;
import services.Localizacion.Localizador;

import java.io.IOException;
import java.util.List;
@Getter
public abstract class Entidad {
    protected String nombre;
    protected List<Establecimiento> establecimientos;
    protected TipoEntidad tipoEntidad;
    protected Localizacion localizacion;

    public void agregarEstablecimiento(Establecimiento establecimiento, int id) throws IOException {
        establecimientos.add(establecimiento);
        this.localizacion = Localizador.devolverLocalizacion(id);
    }


}
