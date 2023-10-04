package server;

import controllers.FactoryController;
import controllers.MenuPrincipalController;
import controllers.PerfilController;
import controllers.RankingsController;
import models.domain.Usuario.TipoRol;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
  public static void init() {
    Server.app().get("/", ctx -> {
      ctx.sessionAttribute("item1", "Cosa 1");
      ctx.result("Hola mundo");
    });
    Server.app().get("/saluda", ctx -> {
      ctx.result("Hola "
          + ctx.queryParam("nombre")
          + ", " + ctx.sessionAttribute("item1")
      );
    });
    Server.app().get("/saludo-para/{nombre}", ctx -> ctx.result("Hola "
        + ctx.pathParam("nombre")
    ));
    Server.app().routes(() -> {
      get("/rankings", ((RankingsController) FactoryController.controller("rankings"))::index);//,TipoRol.USUARIO_EMPRESA,TipoRol.ADMINISTRADOR);
      get("/menu", ((MenuPrincipalController) FactoryController.controller("menuPrincipal"))::index);
      get("/inicioDeSesion", ctx -> ctx.render("InicioDeSesion.hbs"));
      get("perfil",((PerfilController) FactoryController.controller("perfil"))::index);
    });
  }
}
