package persistence.Repositorios;

import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeReceptoresDeNotificaciones extends RepositorioGenerico<ReceptorDeNotificaciones> {
  private static RepositorioDeReceptoresDeNotificaciones instancia = null;

  private RepositorioDeReceptoresDeNotificaciones() {
    super(ReceptorDeNotificaciones.class);
  }
  public static  RepositorioDeReceptoresDeNotificaciones getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeReceptoresDeNotificaciones();
    }
    return instancia;
  }
}
