package controllers;

import io.javalin.http.Context;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.RepositorioComunidad;
import server.utils.ICrudViewsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComunidadesController extends ControllerGenerico implements ICrudViewsHandler {
  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = true;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    boolean yaPertenece = false;
    List<Comunidad> usuarioComunidades = new ArrayList<>();
    List<Comunidad> comunidadesParaUnirse = new ArrayList<>();
    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
    List<Comunidad> finalUsuarioComunidades = usuarioComunidades;

    /*if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
      //miembroDeComunidad = TODO OBTENER EL MIEMBRO DE COMUNIDAD DESDE EL USUARIO
      usuarioComunidades = miembroDeComunidad.getComunidades();
      comunidadesParaUnirse = repositorioComunidad.buscarTodos().stream().filter(comunidad -> !finalUsuarioComunidades.contains(comunidad)).toList();
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
      //miembroDeComunidad = TODO OBTENER EL MIEMBRO DE COMUNIDAD DESDE EL USUARIO
      usuarioComunidades = miembroDeComunidad.getComunidades();
      //este usuario tiene comunidades?
      comunidadesParaUnirse = repositorioComunidad.buscarTodos().stream().filter(comunidad -> !finalUsuarioComunidades.contains(comunidad)).toList();

    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
      usuarioComunidades = repositorioComunidad.buscarTodos();
      //el administrador puede ver todas las comunidades pero no puede unirse
      finalUsuarioComunidades = repositorioComunidades.buscarTodos();
    }
     */

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("comunidades",comunidadesParaUnirse);
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

  }

  @Override
  public void delete(Context context) {

  }
}
