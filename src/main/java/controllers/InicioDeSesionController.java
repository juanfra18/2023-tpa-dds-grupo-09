package controllers;

import io.javalin.http.Context;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import server.exceptions.AccesoDenegadoExcepcion;
import server.handlers.SessionHandler;

import java.util.List;

public class InicioDeSesionController {

  public void validarCredenciales(Context context){
    RepositorioDeUsuarios repositorioDeUsuarios = RepositorioDeUsuarios.getInstancia();
    String usuario = context.formParam("usuario");
    String contrasenia = context.formParam("contrasenia");
    List<Usuario> usuarioPosible = repositorioDeUsuarios.buscarTodos().stream().
                                  filter(u ->  u.getUsername().equals(usuario) && u.getContrasenia().equals(contrasenia)).toList();

    if(usuarioPosible.size()>0)
    {
      SessionHandler.createSession(context,usuarioPosible.get(0).getId(),usuarioPosible.get(0).getRol().getTipo().toString());
      context.redirect("/menu");
    }
    else
    {
      throw new AccesoDenegadoExcepcion();
    }
  }
}

