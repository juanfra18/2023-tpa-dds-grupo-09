package controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import models.domain.Incidentes.EstadoIncidente;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeEstablecimientos;
import models.persistence.Repositorios.RepositorioDeIncidentes;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import models.persistence.Repositorios.RepositorioEntidad;
import server.exceptions.AccesoDenegadoExcepcion;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentesController extends ControllerGenerico implements ICrudViewsHandler {
  RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();

  @Override
  public void index(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    List<Incidente> incidentes = new ArrayList<>();

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
      incidentes = repositorioDeIncidentes.buscarTodos();
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("incidentes",incidentes);
    model.put("miembro_id",miembroDeComunidad.getId());
    model.put("seleccion",false);
    model.put("abierto",false);
    model.put("cerrado",false);
    context.render("ConsultaDeIncidentes.hbs", model);
    em.close();
  }

  public void indexEstado(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    String estado = context.pathParam("estado");

    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    List<Incidente> incidentes = new ArrayList<>();

    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
      incidentes = miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.valueOf(estado));
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
      incidentes = miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.valueOf(estado));
      //TODO revisar si el usuario empresa busca asi los incidentes
    }
    boolean abierto = false;
    boolean cerrado = false;
    if(estado.equals("ABIERTO"))
      abierto = true;
    else
      cerrado = true;

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("incidentes",incidentes);
    model.put("miembro_id",miembroDeComunidad.getId());
    model.put("abierto",abierto);
    model.put("cerrado",cerrado);
    model.put("seleccion",true);
    context.render("ConsultaDeIncidentes.hbs", model);
    em.close();
  }

  @Override
  public void show(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    String estado = context.pathParam("estado");

    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    List<Incidente> incidentes = new ArrayList<>();

    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
      incidentes = miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.valueOf(estado));
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
      incidentes = miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.valueOf(estado));
      //TODO revisar si el usuario empresa busca asi los incidentes
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("incidentes",incidentes);
    model.put("miembro_id",miembroDeComunidad.getId());
    context.render("ConsultaDeIncidentes.hbs",model);
    context.status(200);
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

  }
}