package controllers;

import io.javalin.http.Context;
import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.RepositorioComunidad;
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
    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    Usuario uu = new Usuario();
    ReceptorDeNotificaciones receptorDeNotificaciones = new ReceptorDeNotificaciones();
    RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();

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
    //miembroDeComunidad = TODO obtener el miembro de comunidad a partir del usuario

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("miembroDeComunidad",miembroDeComunidad);
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
