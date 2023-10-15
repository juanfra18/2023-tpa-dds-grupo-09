package controllers;

import io.javalin.http.Context;
import models.domain.Incidentes.EstadoIncidente;
import models.domain.Incidentes.ReporteDeIncidente;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.*;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReporteDeIncidenteController extends ControllerGenerico implements ICrudViewsHandler {
  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = true;
    boolean usuarioEmpresa = false;
    boolean administrador = false;
    String estado = context.pathParam("estado");
    boolean abierto = false;
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
    RepositorioDeEstablecimientos repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();

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


    if(estado.equals("abierto"))
    {
      abierto = true;
    }
    else
    {
      abierto = false;
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("abierto",abierto); //Pasando el string tuve problemas para hacer los if en hbs (no funcionaban)
    model.put("estado",estado);
    model.put("comunidades", miembroDeComunidad.getComunidades());
    model.put("entidades", repositorioEntidad.buscarTodos());
    model.put("establecimientos", repositorioDeEstablecimientos.buscarTodos());
    model.put("miembro_id",miembroDeComunidad.getId());
    context.render("ReporteDeIncidente.hbs", model);
  }


  @Override
  public void save(Context context) {
    String estado = context.pathParam("estado"); //TODO Eliminar del formulario el estado
    String entidad = context.formParam("entidad");
    String comunidad = context.formParam("comunidad");
    String observaciones = context.formParam("observaciones");
    String servicio = context.formParam("servicio");
    String establecimiento = context.formParam("establecimiento");
    String fechaYhora = context.formParam("fechaYhora");
    Comunidad comunidad1 = new Comunidad();
    EntityManager entityManager = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,entityManager);
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    RepositorioEntidad repositorioEntidad=RepositorioEntidad.getInstancia();
    RepositorioServicio repositorioServicio=RepositorioServicio.getInstancia();
    RepositorioDeEstablecimientos repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();
    RepositorioComunidad repositorioComunidad=RepositorioComunidad.getInstancia();
    ReporteDeIncidente reporteDeIncidente = new ReporteDeIncidente();
    reporteDeIncidente.setClasificacion(EstadoIncidente.valueOf(estado));
    reporteDeIncidente.setDenunciante(miembroDeComunidad);
    reporteDeIncidente.setEntidad(repositorioEntidad.buscar(Long.parseLong(entidad)));
    reporteDeIncidente.setObservaciones(observaciones);
    reporteDeIncidente.setServicio(repositorioServicio.buscar(Long.parseLong(servicio)));
    reporteDeIncidente.setFechaYhora(LocalDateTime.parse(fechaYhora));
    reporteDeIncidente.setEstablecimiento(repositorioDeEstablecimientos.buscar(Long.parseLong(establecimiento)));

    RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
    comunidad1= repositorioComunidad.buscar(Long.parseLong(comunidad));

    try {
      entityManager.getTransaction().begin();
      repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente);
      if(comunidad1.getNombre().equals(comunidad)) {
        comunidad1.guardarIncidente(reporteDeIncidente);
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    } finally {
      entityManager.close();
    }

    context.redirect("/menu"); //TODO hace falta hbs?
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
