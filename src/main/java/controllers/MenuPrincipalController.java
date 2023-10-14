package controllers;

import io.javalin.http.Context;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import server.exceptions.AccesoDenegadoExcepcion;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class MenuPrincipalController extends ControllerGenerico implements ICrudViewsHandler  {

  @Override
  public void index(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context, em);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;

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
    model.put("miembro_id",this.miembroDelUsuario(usuarioLogueado.getId().toString()).getId());
    context.render("MenuPrincipal.hbs", model);
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
}
