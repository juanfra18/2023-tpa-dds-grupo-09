package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.domain.Usuario.Usuario;

public abstract class ControllerGenerico implements WithSimplePersistenceUnit {
  protected Usuario usuarioLogueado(Context ctx) {
    if(ctx.sessionAttribute("usuario_id") == null)
      return null;
    return entityManager()
        .find(Usuario.class, Long.parseLong(ctx.sessionAttribute("usuario_id")));
  }


}
