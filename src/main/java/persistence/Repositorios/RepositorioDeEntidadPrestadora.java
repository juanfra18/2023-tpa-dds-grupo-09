package persistence.Repositorios;

import domain.Entidades.*;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeEntidadPrestadora extends RepositorioGenerico<EntidadPrestadora> {
    private static RepositorioDeEntidadPrestadora instancia = null;
    private RepositorioDeEntidadPrestadora() {
        super(EntidadPrestadora.class);
    }
    public static  RepositorioDeEntidadPrestadora getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioDeEntidadPrestadora();
        }
        return instancia;
    }

}
