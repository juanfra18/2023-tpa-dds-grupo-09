package controllers;

import io.javalin.http.Context;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioComunidad;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.*;

public class ComunidadesController extends ControllerGenerico implements ICrudViewsHandler {
  RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = false;
    boolean administrador = false;
    List<Comunidad> usuarioComunidades ;
    List<Comunidad> comunidades = new ArrayList<>();

    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    String id = context.pathParam("id");

    miembroDeComunidad = this.miembroDelUsuario(id);

    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
      usuarioComunidades = miembroDeComunidad.getComunidades();
      comunidades = repositorioComunidad.buscarTodos().stream().filter(comunidad -> !usuarioComunidades.contains(comunidad)).toList();
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
      comunidades = repositorioComunidad.buscarTodos();
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",false);
    model.put("administrador",administrador);
    model.put("comunidades",comunidades);
    model.put("usuario_id",usuarioLogueado.getId());
    context.render("UnirseComunidad.hbs", model);
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
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String comunidadId = context.pathParam("id");
    EntityManager em = EntityManagerSingleton.getInstance();
    Comunidad comunidad = repositorioComunidad.buscar(Long.parseLong(comunidadId));

    comunidad.agregarMiembro(miembroDeComunidad);
    miembroDeComunidad.agregarComunidad(comunidad);

    try {
      em.getTransaction().begin();
      repositorioComunidad.agregar(comunidad);
      em.getTransaction().commit();
      context.redirect("/comunidades/"+ usuarioLogueado.getId());
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }

  @Override
  public void delete(Context context) {
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    String comunidadId = context.pathParam("id");
    EntityManager em = EntityManagerSingleton.getInstance();
    try {
      em.getTransaction().begin();
      Comunidad comunidadAEliminar = repositorioComunidad.buscar(Long.parseLong(comunidadId));

      comunidadAEliminar.getMiembros().forEach(miembroDeComunidad -> miembroDeComunidad.abandonarComunidad(comunidadAEliminar));

      repositorioComunidad.eliminar(comunidadAEliminar);
      em.getTransaction().commit();
      context.redirect("/comunidades/"+ usuarioLogueado.getId());
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}
