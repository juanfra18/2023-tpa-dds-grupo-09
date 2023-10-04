package server.middlewares;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import models.domain.Usuario.TipoRol;
import server.exceptions.AccesoDenegadoExcepcion;

public class AutenticacionMiddleware {
  public static void apply(JavalinConfig config) {
    config.accessManager(((handler, context, routeRoles) -> {
      TipoRol userRole = getUserRoleType(context);

      if(routeRoles.size() == 0 || routeRoles.contains(userRole)) { //falta ver si esta la sesion
        handler.handle(context);
      }
      else {
        throw new AccesoDenegadoExcepcion();
      }
    }));
  }

  private static TipoRol getUserRoleType(Context context) {
    return context.sessionAttribute("tipo_rol") != null?
        TipoRol.valueOf(context.sessionAttribute("tipo_rol")) : null;
  }
}
