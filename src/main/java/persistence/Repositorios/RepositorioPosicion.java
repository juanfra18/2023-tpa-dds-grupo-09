package persistence.Repositorios;

import domain.Incidentes.Posicion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioPosicion extends RepositorioGenerico<Posicion> {
    private static RepositorioPosicion instancia = null;

    private RepositorioPosicion() {
        super(Posicion.class);
    }
    public static RepositorioPosicion getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioPosicion();
        }
        return instancia;
    }
}