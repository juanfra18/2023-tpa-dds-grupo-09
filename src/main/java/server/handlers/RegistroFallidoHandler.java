package server.handlers;

import controllers.FactoryController;
import controllers.RegistrarController;
import io.javalin.Javalin;
import models.Config.Config;
import server.exceptions.ContraseniaInvalida;
import server.exceptions.SesionNoIniciadaExcepcion;
import server.exceptions.UsuarioRepetidoExcepcion;

public class RegistroFallidoHandler implements IHandler {
  @Override
  public void setHandle(Javalin app) {
    app.exception(UsuarioRepetidoExcepcion.class, (e, context) -> {
      ((RegistrarController) FactoryController.controller("registrar")).index(context, "El usuario ingresado ya existe");
    });
    app.exception(ContraseniaInvalida.class, (e, context) -> {
      ((RegistrarController) FactoryController.controller("registrar")).index(context, "Contraseña inválida. La misma debe tener al menos una letra mayúscula, un símbolo, y tener longitud mínima "+ Config.LONGITUD_MINIMA+" caracteres.");
    });
  }
}