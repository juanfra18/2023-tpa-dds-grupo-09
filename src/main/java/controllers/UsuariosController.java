package controllers;

import io.javalin.http.Context;
import models.domain.Usuario.Usuario;
import server.exceptions.AccesoDenegadoExcepcion;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.Map;

public class UsuariosController extends ControllerGenerico implements ICrudViewsHandler {
  @Override
  public void index(Context context) {
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = true;
    boolean usuarioEmpresa = false;
    boolean administrador = false;

    if(usuarioLogueado != null) //deberia ser == pero para probar lo cambie
    {
      throw new AccesoDenegadoExcepcion();
    }

    /*    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
    }

 */

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    context.render("PerfilUsuario.hbs", model);
  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
