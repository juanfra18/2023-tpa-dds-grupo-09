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
      get("/cargarEmpresas", ((EmpresasController) FactoryController.controller("empresas"))::index);
      //TODO
      get("/reportarIncidente/{estado}", ((ReporteDeIncidenteController) FactoryController.controller("reporteDeIncidente"))::index);
      //TODO
      get("/sugerenciasDeRevision", ctx -> ctx.render("SugerenciasDeRevisionDeIncidentes.hbs"));
     //TODO
      get("/comunidades", ((ComunidadesController) FactoryController.controller("comunidades"))::index,TipoRol.USUARIO_BASICO,TipoRol.ADMINISTRADOR);
      post("/comunidades/eliminarComunidad/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::delete,TipoRol.ADMINISTRADOR);
      post("/comunidades/unirseAComunidad/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::update);
      get("/comunidades/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::show);
      get("/usuarios",((UsuariosController) FactoryController.controller("usuarios"))::index, TipoRol.ADMINISTRADOR);
      post("/usuarios/eliminar/{id}",((UsuariosController) FactoryController.controller("usuarios"))::delete, TipoRol.ADMINISTRADOR);
      get("/organismosDeControl",((OrganismosDeControlController) FactoryController.controller("organismos"))::index);
      post("/organismosDeControl/eliminar/{id}",((OrganismosDeControlController) FactoryController.controller("organismos"))::delete,TipoRol.ADMINISTRADOR);
      get("/organismosDeControl/{id}/entidadesPrestadoras",((EntidadesPrestadorasController) FactoryController.controller("entidadesPrestadoras"))::index);
      post("/organismosDeControl/{idO}/entidadesPrestadoras/eliminar/{idEP}",((EntidadesPrestadorasController) FactoryController.controller("entidadesPrestadoras"))::delete,TipoRol.ADMINISTRADOR);
      get("/organismosDeControl/{idO}/entidadesPrestadoras/{idEP}/entidades", ((EntidadesController) FactoryController.controller("entidades"))::index);
      post("/organismosDeControl/{idO}/entidadesPrestadoras/{idEP}/entidades/eliminar/{idE}",((EntidadesController) FactoryController.controller("entidades"))::delete,TipoRol.ADMINISTRADOR);
    });
  }
}
