package persistence.Repositorios;

import domain.Personas.ParServicioRol;
import domain.Personas.Rol;
import domain.Servicios.Banio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioParServicioRol extends RepositorioGenerico<ParServicioRol> {
  private static RepositorioParServicioRol instancia = null;
  private RepositorioParServicioRol() {
    super(ParServicioRol.class);
  }
  public static RepositorioParServicioRol getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioParServicioRol();
    }
    return instancia;
  }
}