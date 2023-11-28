package controllers;

import io.javalin.http.Context;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioComunidad;
import models.persistence.Repositorios.RepositorioDeIncidentes;
import models.persistence.Repositorios.RepositorioEntidad;
import models.persistence.Repositorios.RepositorioMiembroDeComunidad;
import org.jetbrains.annotations.NotNull;
import server.exceptions.AccesoDenegadoExcepcion;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    boolean perfilAdministrador = false;
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


    Usuario perfilUsuario = miembroDeComunidad.getUsuario();
    if(perfilUsuario.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      perfilAdministrador = false;
      incidentesAbiertos = repositorioDeIncidentes.buscarTodos().stream().filter(incidente -> incidente.fueAbiertoPor(miembroDeComunidad)).toList().size();
      incidentesCerrados = repositorioDeIncidentes.buscarTodos().stream().filter(incidente -> incidente.fueCerradoPor(miembroDeComunidad)).toList().size();
    }
    else if(perfilUsuario.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      perfilAdministrador = false;
    }
    else if(perfilUsuario.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      perfilAdministrador = true;
    }


    if (miembroDeComunidad.getId() != this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId() && !administrador) { // Si no soy admin y no es mi perfil
      throw new AccesoDenegadoExcepcion();
    }


    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("perfilAdministrador",perfilAdministrador);
    model.put("miembroDeComunidad",miembroDeComunidad);
    model.put("incidentesAbiertos", incidentesAbiertos);
    model.put("incidentesCerrados", incidentesCerrados);
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

  public void unirseAComunidad(Context context) {
    RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String comunidadId = context.pathParam("id");
    Comunidad comunidad = repositorioComunidad.buscar(Long.parseLong(comunidadId));

    comunidad.agregarMiembro(miembroDeComunidad);
    miembroDeComunidad.agregarComunidad(comunidad);

    try {
      em.getTransaction().begin();
      repositorioComunidad.agregar(comunidad);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }

  public void perfilPersonal(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    boolean perfilAdministrador = false;

    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();

    MiembroDeComunidad miembroDeComunidad = miembroDelUsuario(usuarioLogueado.getId().toString());
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
      perfilAdministrador = true;
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("perfilAdministrador",perfilAdministrador);
    model.put("miembroDeComunidad",miembroDeComunidad);
    model.put("incidentesAbiertos", incidentesAbiertos);
    model.put("incidentesCerrados", incidentesCerrados);
    context.render("PerfilUsuario.hbs", model);
    em.close();
  }
}
