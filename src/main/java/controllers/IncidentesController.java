package controllers;

import io.javalin.http.Context;
import models.domain.Incidentes.EstadoIncidente;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeIncidentes;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import server.exceptions.AccesoDenegadoExcepcion;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentesController extends ControllerGenerico implements ICrudViewsHandler {

  @Override
  public void index(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = true;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    List<Incidente> incidentesDelUsuario = new ArrayList<>();
    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();

  /*  if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
      //miembroDeComunidad = TODO OBTENER EL MIEMBRO DE COMUNIDAD DESDE EL USUARIO
      incidentesDelUsuario = miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.ABIERTO);
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
      //miembroDeComunidad = TODO OBTENER EL MIEMBRO DE COMUNIDAD DESDE EL USUARIO
      incidentesDelUsuario = miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.ABIERTO);
      //QUIZA ACA HAYA QUE BUSCAR LOS INCIDENTES DE ESA EMPRESA?
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
      incidentesDelUsuario = repositorioDeIncidentes.buscarTodos();
      //el administrador puede ver todos los incidentes
    }
   */
    em.close();
    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("incidentes",incidentesDelUsuario);
    context.render("ConsultaDeIncidentes.hbs", model);
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