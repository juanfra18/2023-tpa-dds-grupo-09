package server;

import controllers.*;
import models.domain.Usuario.TipoRol;
import server.handlers.SessionHandler;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
  public static void init() {
    Server.app().routes(() -> {
      post("/cerrarSesion", ctx -> {SessionHandler.endSession(ctx); ctx.status(200);});
      get("/rankings", ((RankingsController) FactoryController.controller("rankings"))::index, TipoRol.USUARIO_EMPRESA,TipoRol.ADMINISTRADOR);
      get("/menu", ((MenuPrincipalController) FactoryController.controller("menuPrincipal"))::index);
      get("/", ctx -> ctx.redirect("/menu"));
      get("/inicioDeSesion", ((InicioDeSesionController) FactoryController.controller("inicioDeSesion"))::verificarSesion);
      post("/inicioDeSesion", ((InicioDeSesionController) FactoryController.controller("inicioDeSesion"))::validarCredenciales);
      get("/perfil/{id}",((UsuariosController) FactoryController.controller("usuarios"))::show);
      get("/incidentes",((IncidentesController) FactoryController.controller("incidentes"))::index);
      get("/cargarEmpresas", ((EmpresasController) FactoryController.controller("empresas"))::update,TipoRol.ADMINISTRADOR);
      post("/cargarEmpresas",((EmpresasController) FactoryController.controller("empresas"))::save,TipoRol.ADMINISTRADOR);
      get("/reportarIncidente/{estado}", ((ReporteDeIncidenteController) FactoryController.controller("reporteDeIncidente"))::create);
      post("/reportarIncidente/{estado}", ((ReporteDeIncidenteController) FactoryController.controller("reporteDeIncidente"))::save);
      get("/sugerenciasDeRevision", ctx -> ctx.render("SugerenciasDeRevisionDeIncidentes.hbs"));
     //TODO
      get("/comunidades", ((ComunidadesController) FactoryController.controller("comunidades"))::index,TipoRol.USUARIO_BASICO,TipoRol.ADMINISTRADOR);
      post("/comunidades/eliminarComunidad/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::delete,TipoRol.ADMINISTRADOR);
      post("/comunidades/unirseAComunidad/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::update);
      get("/comunidades/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::show);
      get("/usuarios",((UsuariosController) FactoryController.controller("usuarios"))::index, TipoRol.ADMINISTRADOR);
      post("/usuarios/eliminar/{id}",((UsuariosController) FactoryController.controller("usuarios"))::delete, TipoRol.ADMINISTRADOR);
      post("usuarios/interes/entidad/{id}",((UsuariosController) FactoryController.controller("usuarios"))::update,TipoRol.USUARIO_BASICO);
      get("/organismosDeControl",((OrganismosDeControlController) FactoryController.controller("organismos"))::index);
      get("/organismosDeControl/{id}/entidadesPrestadoras",((EntidadesPrestadorasController) FactoryController.controller("entidadesPrestadoras"))::index);
      get("/organismosDeControl/{idO}/entidadesPrestadoras/{idEP}/entidades", ((EntidadesController) FactoryController.controller("entidades"))::index);
      get("/organismosDeControl/{idO}/entidadesPrestadoras/{idEP}/entidades/{idE}/establecimientos",((EstablecimientosController) FactoryController.controller("establecimientos"))::index);
      get("/organismosDeControl/{idO}/entidadesPrestadoras/{idEP}/entidades/{idE}/establecimientos/{idES}/servicios",((ServiciosController) FactoryController.controller("servicios"))::index);
      get("/interesEntidad/{id}",((InteresController) FactoryController.controller("interes"))::verificarInteresEntidad,TipoRol.USUARIO_BASICO);
      get("/entidades/{idE}/establecimientos",((EstablecimientosController) FactoryController.controller("establecimientos"))::obtenerEstablecimientos,TipoRol.USUARIO_BASICO);
      get("/entidades/{idE}/establecimientos/{idES}/servicios",((ServiciosController) FactoryController.controller("servicios"))::obtenerServicios,TipoRol.USUARIO_BASICO);
      get("/intereses",((InteresController) FactoryController.controller("interes"))::index,TipoRol.USUARIO_BASICO);
      get("/intereses/entidad/{id}",((InteresController) FactoryController.controller("interes"))::verificarInteresEntidad,TipoRol.USUARIO_BASICO);
      //get("/intereses/servicio/{id}",((InteresController) FactoryController.controller("interes"))::verificarInteresServicio,TipoRol.USUARIO_BASICO);
      post("/intereses/entidad/agregar/{id}",((InteresController) FactoryController.controller("interes"))::agregarEntidad,TipoRol.USUARIO_BASICO);
      post("/intereses/entidad/eliminar/{id}",((InteresController) FactoryController.controller("interes"))::eliminarEntidad,TipoRol.USUARIO_BASICO);
     // post("/intereses/servicio/agregar/{id}",((InteresController) FactoryController.controller("interes"))::agregarServicio,TipoRol.USUARIO_BASICO);
      //post("/intereses/servicio/eliminar/{id}",((InteresController) FactoryController.controller("interes"))::eliminarServicio,TipoRol.USUARIO_BASICO);
    });
  }
}
