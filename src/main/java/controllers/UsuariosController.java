package controllers;

import io.javalin.http.Context;
import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioComunidad;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import models.persistence.Repositorios.RepositorioMiembroDeComunidad;
import server.exceptions.AccesoDenegadoExcepcion;
import server.handlers.SessionHandler;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuariosController extends ControllerGenerico implements ICrudViewsHandler {
  RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean administrador = false;

    List<MiembroDeComunidad> miembrosDeComunidadDelSistema = repositorioMiembroDeComunidad.buscarTodos();

    if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
    }
    model.put("administrador",administrador);
    model.put("miembros",miembrosDeComunidadDelSistema);
    model.put("usuario_id",usuarioLogueado.getId().toString());
    context.render("AdministracionUsuarios.hbs", model);
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    String id = context.pathParam("id");

    miembroDeComunidad = this.miembroDelUsuario(id);

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

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("miembroDeComunidad",miembroDeComunidad);
    model.put("usuario_id",usuarioLogueado.getId().toString());
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
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    String miembroId = context.pathParam("id");
    EntityManager em = EntityManagerSingleton.getInstance();

      try {
        em.getTransaction().begin();
        MiembroDeComunidad miembroDeComunidadAEliminar = repositorioMiembroDeComunidad.buscar(Long.parseLong(miembroId));
        repositorioMiembroDeComunidad.eliminar(miembroDeComunidadAEliminar);
        em.getTransaction().commit();
        context.redirect("/administrarUsuarios");
      } catch (Exception e) {
        em.getTransaction().rollback();
      } finally {
        em.close();
      }
  }
}
