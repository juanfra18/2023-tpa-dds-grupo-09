package persistence.Repositorios;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEntidad;
import domain.Entidades.TipoEstablecimiento;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioEntidad extends RepositorioGenerico<Entidad> {
    private static RepositorioEntidad instancia = null;
    private RepositorioEntidad() {
        super(Entidad.class);
    }
    public static RepositorioEntidad getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioEntidad();
        }
        return instancia;
    }

}