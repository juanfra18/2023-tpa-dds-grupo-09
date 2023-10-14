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
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean administrador = false;

    List<MiembroDeComunidad> miembrosDeComunidadDelSistema = repositorioMiembroDeComunidad.buscarTodos();

    if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
    }
    model.put("administrador",administrador);
    model.put("miembros",miembrosDeComunidadDelSistema);
    model.put("miembro_id",this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId());
    context.render("AdministracionUsuarios.hbs", model);
    em.close();
  }

  @Override
  public void show(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    String id = context.pathParam("id");
    boolean miPerfil = false;

    miembroDeComunidad = repositorioMiembroDeComunidad.buscar(Long.parseLong(id));

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

    if(miembroDeComunidad.getId().equals(this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId()))
        miPerfil = true;
    //Como se compara directamente en handlebars dentro de un if?



    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("miembroDeComunidad",miembroDeComunidad);
    model.put("miembro_id",this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId());
    model.put("miPerfil", miPerfil);
    context.render("PerfilUsuario.hbs", model);
    em.close();
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
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    String miembroId = context.pathParam("id");

    try {
      em.getTransaction().begin();
      MiembroDeComunidad miembroDeComunidadAEliminar = repositorioMiembroDeComunidad.buscar(Long.parseLong(miembroId));
      repositorioMiembroDeComunidad.eliminar(miembroDeComunidadAEliminar);
      em.getTransaction().commit();
      if (usuarioLogueado.getId()==Long.parseLong(miembroId))
      { context.redirect("/inicioDeSesion"); }
      else context.redirect("/usuarios");
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}
