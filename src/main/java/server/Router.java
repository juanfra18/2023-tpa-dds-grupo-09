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
      get("/inicioDeSesion", ctx -> ctx.render("inicioDeSesion.hbs"));
      post("/inicioDeSesion", ((InicioDeSesionController) FactoryController.controller("inicioDeSesion"))::validarCredenciales);
      get("perfil",((UsuariosController) FactoryController.controller("perfil"))::show);
      get("incidentes",((IncidentesController) FactoryController.controller("incidentes"))::index);
      get("cargarEmpresas", ((EmpresasController) FactoryController.controller("empresas"))::index);
      //deberia ser un post no?
      get("reportarIncidente/{estado}", ((ReporteDeIncidenteController) FactoryController.controller("reporteDeIncidente"))::index);
      //deberia ser un post no?
      get("/sugerenciasDeRevision", ctx -> ctx.render("SugerenciasDeRevisionDeIncidentes.hbs"));
      get("comunidades", ((ComunidadesController) FactoryController.controller("comunidades"))::index);
    });
  }
}
