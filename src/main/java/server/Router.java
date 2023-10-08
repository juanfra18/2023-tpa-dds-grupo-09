package server;

import controllers.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
  public static void init() {
    Server.app().routes(() -> {
      get("/rankings", ((RankingsController) FactoryController.controller("rankings"))::index);//,TipoRol.USUARIO_EMPRESA,TipoRol.ADMINISTRADOR);
      get("/menu", ((MenuPrincipalController) FactoryController.controller("menuPrincipal"))::index);
      get("/inicioDeSesion", ctx -> ctx.render("InicioDeSesion.hbs"));
      get("/", ctx -> ctx.render("InicioDeSesion.hbs"));
      get("perfil",((UsuariosController) FactoryController.controller("perfil"))::show);
      get("incidentes",((IncidentesController) FactoryController.controller("incidentes"))::index);
      get("cargarEmpresas", ((EmpresasController) FactoryController.controller("empresas"))::index);
      //deberia ser un post no?
      get("reportarIncidente/{estado}", ((ReporteDeIncidenteController) FactoryController.controller("reporteDeIncidente"))::index);
      //deberia ser un post no?
      get("/sugerenciasDeRevision", ctx -> ctx.render("SugerenciasDeRevisionDeIncidentes.hbs"));
    });
  }
}
