package persistence.Repositorios;

import domain.Notificaciones.*;


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
