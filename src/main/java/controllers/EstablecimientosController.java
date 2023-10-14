package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Entidades.EntidadPrestadora;
import models.domain.Entidades.Establecimiento;
import models.domain.Entidades.OrganismoDeControl;
import models.domain.Incidentes.Incidente;
import models.domain.Incidentes.ReporteDeIncidente;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.*;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstablecimientosController extends ControllerGenerico implements ICrudViewsHandler{
  RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
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

      Entidad entidad = repositorioEntidad.buscar(Long.parseLong(entidadId));
      List<Establecimiento> establecimientos = entidad.getEstablecimientos();
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
      model.put("establecimientos",establecimientos);
      model.put("miembro_id",miembroDeComunidad.getId());
      model.put("organismo_id",organismoId);
      model.put("entidadPrestadora_id",entidadPrestadoraId);
      model.put("entidad_id",entidadId);
      context.render("Establecimientos.hbs", model);
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
    String entidadPrestadoraId = context.pathParam("idEP");
    String organismoId = context.pathParam("idO");
    String entidadId = context.pathParam("idE");
    String establecimientoId = context.pathParam("idES");
    EntityManager em = EntityManagerSingleton.getInstance();
    try {
      em.getTransaction().begin();
      Establecimiento establecimientoAEliminar = repositorioDeEstablecimientos.buscar(Long.parseLong(establecimientoId));


      //Desvincularla de entidad, reportes e incidentes
      repositorioEntidad.buscar(Long.parseLong(entidadId)).eliminarEstablecimiento(establecimientoAEliminar);

      RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
      List<ReporteDeIncidente> reportesAEliminar = repositorioDeReportesDeIncidentes.buscarTodos().stream().filter(reporteDeIncidente -> reporteDeIncidente.getEstablecimiento().equals(establecimientoAEliminar)).toList();

      RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
      List<Incidente> incidentesAEliminar = repositorioDeIncidentes.buscarTodos().stream().filter(incidente -> incidente.getEstablecimiento().equals(establecimientoAEliminar)).toList();

      //Desvincular reportes de comunidades
      RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
      repositorioComunidad.buscarTodos().stream().forEach(comunidad -> comunidad.eliminarReportesAEliminar(reportesAEliminar));

      incidentesAEliminar.forEach(incidente -> repositorioDeIncidentes.eliminar(incidente));


     // reportesAEliminar.forEach(reporte -> repositorioDeReportesDeIncidentes.eliminar(reporte));


      repositorioDeEstablecimientos.eliminar(establecimientoAEliminar);
      em.getTransaction().commit();
      context.redirect("/organismosDeControl/"+organismoId+"/entidadesPrestadoras/" + entidadPrestadoraId + "/entidades/" + entidadId + "/establecimientos");
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}
