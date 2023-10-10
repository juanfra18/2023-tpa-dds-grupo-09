package controllers;

import io.javalin.http.Context;
import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.RepositorioComunidad;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import models.persistence.Repositorios.RepositorioMiembroDeComunidad;
import server.exceptions.AccesoDenegadoExcepcion;
import server.handlers.SessionHandler;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuariosController extends ControllerGenerico implements ICrudViewsHandler {
  @Override
  public void index(Context context) {
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    Optional<MiembroDeComunidad> posibleMiembro = Optional.of(new MiembroDeComunidad());
    List<MiembroDeComunidad> miembrosDeComunidadDelSistema;
    RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();

    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
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

    miembrosDeComunidadDelSistema = repositorioMiembroDeComunidad.buscarTodos();

    if(!miembrosDeComunidadDelSistema.isEmpty())
    {
    posibleMiembro = miembrosDeComunidadDelSistema.stream().
          filter(miembro -> miembro.getUsuario().getId().equals(Long.parseLong(SessionHandler.getUserID(context)))).findFirst();
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    if(posibleMiembro.isPresent())
      model.put("miembroDeComunidad",posibleMiembro.get());
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
