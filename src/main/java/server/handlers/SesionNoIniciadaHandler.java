package server.handlers;

import io.javalin.Javalin;
import server.exceptions.AccesoDenegadoExcepcion;
import server.exceptions.SesionNoIniciadaExcepcion;

public class SesionNoIniciadaHandler implements IHandler {
  @Override
  public void setHandle(Javalin app) {
    app.exception(SesionNoIniciadaExcepcion.class, (e, context) -> {
      context.redirect("/inicioDeSesion");
    });
  }
}
