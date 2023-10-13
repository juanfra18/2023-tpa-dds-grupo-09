package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Entidades.EntidadPrestadora;
import models.domain.Entidades.OrganismoDeControl;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeOrganismosDeControl;
import models.persistence.Repositorios.RepositorioMiembroDeComunidad;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganismosDeControlController extends ControllerGenerico implements ICrudViewsHandler{
  RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    List<OrganismoDeControl> organismos = repositorioDeOrganismosDeControl.buscarTodos();
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

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
    model.put("organismos",organismos);
    model.put("miembro_id",miembroDeComunidad.getId());
    context.render("OrganismosDeControl.hbs", model);

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
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    String organismoId = context.pathParam("id");
    EntityManager em = EntityManagerSingleton.getInstance();
    try {
      em.getTransaction().begin();
      OrganismoDeControl organismoAEliminar = repositorioDeOrganismosDeControl.buscar(Long.parseLong(organismoId));
      List<Entidad> entidadesAEliminar = new ArrayList<>();

      for(EntidadPrestadora entidadPrestadora:organismoAEliminar.getEntidadesPrestadoras())
      {
        entidadesAEliminar.addAll(entidadPrestadora.getEntidades());
      }

      RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
      repositorioMiembroDeComunidad.buscarTodos().forEach(miembroDeComunidad -> miembroDeComunidad.perderInteres(entidadesAEliminar));

      repositorioDeOrganismosDeControl.eliminar(organismoAEliminar);
      em.getTransaction().commit();
      context.redirect("/organismosDeControl");
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}
