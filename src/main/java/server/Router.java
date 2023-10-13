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
      get("/inicioDeSesion", ((InicioDeSesionController) FactoryController.controller("inicioDeSesion"))::verificarSesion);
      post("/inicioDeSesion", ((InicioDeSesionController) FactoryController.controller("inicioDeSesion"))::validarCredenciales);
      get("/perfil/{id}",((UsuariosController) FactoryController.controller("usuarios"))::show);
      get("/incidentes",((IncidentesController) FactoryController.controller("incidentes"))::index);
      get("/cargarEmpresas", ((EmpresasController) FactoryController.controller("empresas"))::index);
      //deberia ser un post no?
      get("/reportarIncidente/{estado}", ((ReporteDeIncidenteController) FactoryController.controller("reporteDeIncidente"))::index);
      //deberia ser un post no?
      get("/sugerenciasDeRevision", ctx -> ctx.render("SugerenciasDeRevisionDeIncidentes.hbs"));
      get("/comunidades", ((ComunidadesController) FactoryController.controller("comunidades"))::index,TipoRol.USUARIO_BASICO,TipoRol.ADMINISTRADOR);
      post("/comunidades/eliminarComunidad/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::delete,TipoRol.ADMINISTRADOR);
      post("/comunidades/unirseAComunidad/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::update);
      get("/comunidades/{id}",((ComunidadesController) FactoryController.controller("comunidades"))::show);
      get("/usuarios",((UsuariosController) FactoryController.controller("usuarios"))::index, TipoRol.ADMINISTRADOR);
      post("/usuarios/eliminar/{id}",((UsuariosController) FactoryController.controller("usuarios"))::delete, TipoRol.ADMINISTRADOR);
    });
  }
}
