package persistence.Repositorios;

import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioMiembroDeComunidad extends RepositorioGenerico<MiembroDeComunidad> {
  private static RepositorioMiembroDeComunidad instancia = null;
  RepositorioParServicioRol repositorioParServicioRol;

  private RepositorioMiembroDeComunidad() {
    super(MiembroDeComunidad.class);
    repositorioParServicioRol = RepositorioParServicioRol.getInstancia();
  }
  public static RepositorioMiembroDeComunidad getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioMiembroDeComunidad();
    }
    return instancia;
  }
}