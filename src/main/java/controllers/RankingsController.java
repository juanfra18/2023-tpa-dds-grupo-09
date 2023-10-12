package controllers;


import io.javalin.http.Context;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.services.Archivos.SistemaDeArchivos;
import server.exceptions.AccesoDenegadoExcepcion;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingsController extends ControllerGenerico implements ICrudViewsHandler {
  private SistemaDeArchivos sistemaDeArchivos;
  private List<String[]> filasR1;
  private List<String[]> filasR2;


  public RankingsController(){
    this.sistemaDeArchivos = new SistemaDeArchivos();
  }

  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioEmpresa = false;
    boolean administrador = false;


    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
    }


    filasR1 = sistemaDeArchivos.csvALista("resources/ranking1.csv");
    filasR2 = sistemaDeArchivos.csvALista("resources/ranking2.csv");

    model.put("filasR1",filasR1);
    model.put("filasR2",filasR2);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("usuario_id",usuarioLogueado.getId().toString());
    context.render("Rankings.hbs", model);
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
