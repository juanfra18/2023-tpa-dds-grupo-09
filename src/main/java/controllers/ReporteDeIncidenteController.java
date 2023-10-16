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

    String estado = context.pathParam("estado");

    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());
    RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
    RepositorioDeEstablecimientos repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();


    model.put("usuarioBasico",true);
    model.put("estado",estado);
    model.put("comunidades", miembroDeComunidad.getComunidades());
    model.put("entidades", repositorioEntidad.buscarTodos());
    model.put("establecimientos", repositorioDeEstablecimientos.buscarTodos());
    model.put("miembro_id",miembroDeComunidad.getId());
    context.render("ReporteDeIncidente.hbs", model);
    em.close();
  }


  @Override
  public void save(Context context) {
    String estado = context.pathParam("estado");
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
    comunidad1 = repositorioComunidad.buscar(Long.parseLong(comunidad));

    try {
      entityManager.getTransaction().begin();
      miembroDeComunidad.informarFuncionamiento(reporteDeIncidente,comunidad1);
      repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    } finally {
      entityManager.close();
    }
    context.redirect("/reportarIncidente/"+estado);
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
