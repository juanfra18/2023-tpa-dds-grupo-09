package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.domain.Usuario.Usuario;
import server.exceptions.AccesoDenegadoExcepcion;

public abstract class ControllerGenerico implements WithSimplePersistenceUnit {
  protected Usuario usuarioLogueado(Context ctx) {
    if(ctx.sessionAttribute("usuario_id") == null) {
      //throw new SesionNoIniciadaExcepcion();
      return null;
    }
    return entityManager()
        .find(Usuario.class, Long.parseLong(ctx.sessionAttribute("usuario_id")));
  }


}
