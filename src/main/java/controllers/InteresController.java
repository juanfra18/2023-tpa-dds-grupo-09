package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Personas.ParServicioRol;
import models.domain.Personas.Rol;
import models.domain.Servicios.Servicio;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioEntidad;
import models.persistence.Repositorios.RepositorioParServicioRol;
import models.persistence.Repositorios.RepositorioServicio;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteresController extends ControllerGenerico implements ICrudViewsHandler {
  RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
  RepositorioServicio repositorioServicio = RepositorioServicio.getInstancia();
  RepositorioParServicioRol repositorioParServicioRol = RepositorioParServicioRol.getInstancia();

  public void verificarInteresEntidad(Context context){
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    Entidad entidad = repositorioEntidad.buscar(Long.parseLong(context.pathParam("id")));
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    boolean interes = miembroDeComunidad.esEntidadDeInteres(entidad);
    em.close();
    context.json(interes);
  }

  public void verificarInteresServicio(Context context){
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    Servicio servicio = repositorioServicio.buscar(Long.parseLong(context.pathParam("id")));
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    boolean interes = miembroDeComunidad.esServicioDeInteres(servicio);
    em.close();
    context.json(interes);
  }
  @Override
  public void index(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    List<Entidad> entidades = miembroDeComunidad.getEntidadesDeInteres();

    List<ParServicioRol> servicios = miembroDeComunidad.getServiciosDeInteres();


    model.put("usuarioBasico",true);
    model.put("entidades",entidades);
    model.put("servicios",servicios);
    model.put("miembro_id",miembroDeComunidad.getId());
    context.render("Intereses.hbs", model);
    em.close();
  }

  @Override
  public void show(Context context) {

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

  public void agregarEntidad(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String entidadId = context.pathParam("id");
    try {
      em.getTransaction().begin();
      Entidad nuevaEntidadInteres = repositorioEntidad.buscar(Long.parseLong(entidadId));
      miembroDeComunidad.agregarEntidadDeInteres(nuevaEntidadInteres);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
  public void eliminarEntidad(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String entidadId = context.pathParam("id");
    try {
      em.getTransaction().begin();
      Entidad entidadAEliminar = repositorioEntidad.buscar(Long.parseLong(entidadId));
      miembroDeComunidad.eliminarEntidadDeInteres(entidadAEliminar);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }

  public void agregarServicio(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String servicioId = context.pathParam("id");
    String rol = context.pathParam("rol");
    try {
      em.getTransaction().begin();
      Servicio nuevoServicioDeInteres = repositorioServicio.buscar(Long.parseLong(servicioId));
      miembroDeComunidad.agregarServicioDeInteres(nuevoServicioDeInteres, Rol.valueOf(rol));
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
  public void eliminarServicio(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String servicioId = context.pathParam("id");
    try {
      em.getTransaction().begin();
      ParServicioRol parServicioRolAEliminar = repositorioParServicioRol.buscar(Long.parseLong(servicioId));
      miembroDeComunidad.eliminarServicioDeInteres(parServicioRolAEliminar);
      //En una relacion OneToMany no elimina la fila hibernate con tan solo quitarlo de la lista del miebro de comunidad
      repositorioParServicioRol.eliminar(parServicioRolAEliminar);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }

  public void cambiarRol(Context context){
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String servicioId = context.pathParam("id");
    String rol = context.pathParam("rol");

    try {
      em.getTransaction().begin();
      ParServicioRol parServicioRolAModificar = repositorioParServicioRol.buscar(Long.parseLong(servicioId));
      miembroDeComunidad.cambiarRolSobreServicio(parServicioRolAModificar.getServicio());
      //En una relacion OneToMany no elimina la fila hibernate con tan solo quitarlo de la lista del miebro de comunidad
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}
