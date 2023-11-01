package server.handlers;

import controllers.EntidadesPrestadorasController;
import controllers.FactoryController;
import controllers.OrganismosDeControlController;
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
      if(context.path().equals("/")) ((RegistrarController) FactoryController.controller("registrar")).index(context, "El usuario ingresado ya existe");
      else if(context.path().equals("/organismoDeControl/representante")) ((OrganismosDeControlController) FactoryController.controller("organismos")).error(context, "El usuario ingresado ya existe");
      else ((EntidadesPrestadorasController) FactoryController.controller("entidadesPrestadoras")).error(context, "El usuario ingresado ya existe");
    });
    app.exception(ContraseniaInvalida.class, (e, context) -> {
      if(context.path().equals("/")) ((RegistrarController) FactoryController.controller("registrar")).index(context, "Contraseña inválida. La misma debe tener al menos una letra mayúscula, un símbolo, y tener longitud mínima "+ Config.LONGITUD_MINIMA+" caracteres.");
      else if(context.path().equals("/organismoDeControl/representante")) ((OrganismosDeControlController) FactoryController.controller("organismos")).error(context, "Contraseña inválida. La misma debe tener al menos una letra mayúscula, un símbolo, y tener longitud mínima "+ Config.LONGITUD_MINIMA+" caracteres.");
      else ((EntidadesPrestadorasController) FactoryController.controller("entidadesPrestadoras")).error(context, "Contraseña inválida. La misma debe tener al menos una letra mayúscula, un símbolo, y tener longitud mínima "+ Config.LONGITUD_MINIMA+" caracteres.");
    });
  }
}