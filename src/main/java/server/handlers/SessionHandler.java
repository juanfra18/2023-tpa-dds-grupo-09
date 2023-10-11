package server.handlers;

import io.javalin.http.Context;
import models.domain.Usuario.Usuario;

public class SessionHandler {

  public static void createSession(Context context, long id, String tipoRol ){
    context.cookie("usuario_id",String.valueOf(id));
    context.cookie("tipo_rol", tipoRol);
  }

  public static boolean checkSession(Context ctx) {
    return ctx.cookie("usuario_id") != null;
  }

  public static String getUserID(Context ctx) {
    return ctx.cookie("usuario_id");
  }
  public static String getTipoRol(Context ctx) {
    return ctx.cookie("tipo_rol");
  }

  public static void endSession(Context ctx) {
    ctx.removeCookie("usuario_id");
    ctx.removeCookie("tipo_rol");
  }
}
