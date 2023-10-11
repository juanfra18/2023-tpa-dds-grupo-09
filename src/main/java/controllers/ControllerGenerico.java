package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.domain.Usuario.Usuario;
import server.exceptions.SesionNoIniciadaExcepcion;
import server.handlers.SessionHandler;

public abstract class ControllerGenerico implements WithSimplePersistenceUnit {
  protected Usuario usuarioLogueado(Context ctx) {
    if(!SessionHandler.checkSession(ctx)) {
      throw new SesionNoIniciadaExcepcion();
    }
    return entityManager()
        .find(Usuario.class, Long.parseLong(SessionHandler.getUserID(ctx)));
  }


}
