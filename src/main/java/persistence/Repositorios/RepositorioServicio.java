package persistence.Repositorios;

import domain.Servicios.*;


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