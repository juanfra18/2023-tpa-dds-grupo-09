package controllers;

import io.javalin.http.Context;
import models.domain.Seguridad.ValidadorDeContrasenias;
import models.domain.Usuario.Rol;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import server.exceptions.ContraseniaInvalida;
import server.exceptions.UsuarioRepetidoExcepcion;
import server.handlers.SessionHandler;

import javax.persistence.EntityManager;
import java.util.List;

public class RegistrarController {

  public void registrarUsuario(Context context){
    RepositorioDeUsuarios repositorioDeUsuarios = RepositorioDeUsuarios.getInstancia();
    ValidadorDeContrasenias validadorDeContrasenias = new ValidadorDeContrasenias();
    EntityManager em = EntityManagerSingleton.getInstance();
    String usuario = context.formParam("usuario");
    String contrasenia = context.formParam("contrasenia");
    List<String> usuariosRegistrados = repositorioDeUsuarios.buscarTodos().stream().map(usuario1 -> usuario1.getUsername()).toList();

    if(usuariosRegistrados.contains(usuario))
    {
      throw new UsuarioRepetidoExcepcion();
    }
    else if(!validadorDeContrasenias.verificaReglas(contrasenia))
    {
      throw new ContraseniaInvalida();
    }
    else{
      Usuario nuevoUsuario = new Usuario();
      nuevoUsuario.setUsername(usuario);
      nuevoUsuario.setContrasenia(contrasenia);

      Rol basico = new Rol();
      basico.setNombre("basico");
      basico.setTipo(TipoRol.USUARIO_BASICO);
      nuevoUsuario.setRol(basico);

      repositorioDeUsuarios.agregar(nuevoUsuario);

      SessionHandler.createSession(context,nuevoUsuario.getId(),nuevoUsuario.getRol().getTipo().toString());
      context.redirect("/menu");
    }
  }
}
