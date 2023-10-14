package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Entidades.Establecimiento;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Servicios.Servicio;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.RepositorioDeEstablecimientos;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiciosController extends ControllerGenerico implements ICrudViewsHandler{
  RepositorioDeEstablecimientos repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();
  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    String organismoId = context.pathParam("idO");
    String entidadPrestadoraId = context.pathParam("idEP");
    String entidadId = context.pathParam("idE");
    String establecimientoId = context.pathParam("idES");
    String estado;

    Establecimiento establecimiento = repositorioDeEstablecimientos.buscar(Long.parseLong(establecimientoId));
    List<Servicio> servicios = establecimiento.getServicios();
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
    model.put("servicios",servicios);
    model.put("miembro_id",miembroDeComunidad.getId());
    model.put("organismo_id",organismoId);
    model.put("entidadPrestadora_id",entidadPrestadoraId);
    model.put("entidad_id",entidadId);
    context.render("Servicios.hbs", model);

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
