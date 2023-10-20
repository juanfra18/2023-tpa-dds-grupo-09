package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Incidentes.EstadoIncidente;
import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.*;
import org.jetbrains.annotations.NotNull;
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
  RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
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
    String id = context.pathParam("id");
    boolean miPerfil = false;
    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();

    MiembroDeComunidad miembroDeComunidad = repositorioMiembroDeComunidad.buscar(Long.parseLong(id));
    Integer incidentesAbiertos = 0;
    Integer incidentesCerrados = 0;

    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
      incidentesAbiertos = repositorioDeIncidentes.buscarTodos().stream().filter(incidente -> incidente.fueAbiertoPor(miembroDeComunidad)).toList().size();
      incidentesCerrados = repositorioDeIncidentes.buscarTodos().stream().filter(incidente -> incidente.fueCerradoPor(miembroDeComunidad)).toList().size();
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
    }

    if(miembroDeComunidad.getId().equals(this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId())) { // Si mi ID es del usuario loguado
      miPerfil = true; //Muestrame mi perfil
    }
    else if (miembroDeComunidad.getId() != this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId() && !administrador) { // Si no soy admin y no es mi perfil
      throw new AccesoDenegadoExcepcion();
    }


    //Como se compara directamente en handlebars dentro de un if?
    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("miembroDeComunidad",miembroDeComunidad);
    model.put("incidentesAbiertos", incidentesAbiertos);
    model.put("incidentesCerrados", incidentesCerrados);
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

    public void abandonarComunidad(Context context) {
      EntityManager em = EntityManagerSingleton.getInstance();
      Usuario usuarioLogueado = super.usuarioLogueado(context,em);
      MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
      String id = context.pathParam("id");

      RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();

      Comunidad comunidad = repositorioComunidad.buscar(Long.parseLong(id));

      miembroDeComunidad.abandonarComunidad(comunidad);

      try {
        em.getTransaction().begin();
        repositorioComunidad.agregar(comunidad);
        //update de miembro?
        em.getTransaction().commit();
      }
      catch (Exception e){
        em.getTransaction().rollback();
      }
      finally {
        em.close();
      }
    }
}
