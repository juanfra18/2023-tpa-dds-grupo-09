package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Entidades.EntidadPrestadora;
import models.domain.Entidades.OrganismoDeControl;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeEntidadPrestadora;
import models.persistence.Repositorios.RepositorioDeOrganismosDeControl;
import models.persistence.Repositorios.RepositorioMiembroDeComunidad;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntidadesPrestadorasController extends ControllerGenerico implements ICrudViewsHandler{
  RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
  RepositorioDeEntidadPrestadora repositorioDeEntidadPrestadora = RepositorioDeEntidadPrestadora.getInstancia();
  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    String id = context.pathParam("id");
    OrganismoDeControl organismoDeControl = repositorioDeOrganismosDeControl.buscar(Long.parseLong(id));
    List<EntidadPrestadora> entidadesPrestadoras = organismoDeControl.getEntidadesPrestadoras();
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
    model.put("entidadesPrestadoras",entidadesPrestadoras);
    model.put("miembro_id",miembroDeComunidad.getId());
    model.put("organismo_id",organismoDeControl.getId());
    context.render("EntidadesPrestadoras.hbs", model);
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
}
