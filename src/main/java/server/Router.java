package server;

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
  }
}
