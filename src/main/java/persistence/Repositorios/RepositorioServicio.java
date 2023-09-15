package persistence.Repositorios;

import domain.Incidentes.Incidente;
import domain.Servicios.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioServicio extends RepositorioGenerico<Servicio> {
  private static RepositorioServicio instancia = null;

  private RepositorioServicio() {
    super(Servicio.class);
  }

  public static RepositorioServicio getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioServicio();
    }
    return instancia;
  }
}