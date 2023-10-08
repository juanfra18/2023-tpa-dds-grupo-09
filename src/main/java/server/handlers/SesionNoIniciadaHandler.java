package server.handlers;

import io.javalin.Javalin;
import server.exceptions.AccesoDenegadoExcepcion;

public class SesionNoIniciadaHandler implements IHandler {
  @Override
  public void setHandle(Javalin app) {
    app.exception(AccesoDenegadoExcepcion.class, (e, context) -> {
      context.redirect("/inicioDeSesion");
    });
  }
}
