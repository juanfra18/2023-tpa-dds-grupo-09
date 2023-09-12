package ServicioAPI;

import io.javalin.Javalin;

public class WebApp {
  public static void main(String[] args) {
    RepoEntidad repoEntidad = new RepoEntidad();
    RepoIncidente repoIncidente = new RepoIncidente();
    RepoComunidad repoComunidad = new RepoComunidad();
    Integer port = Integer.parseInt(
        System.getProperty("port", "8080"));
    Javalin app = Javalin.create().start(port);
    app.post("/ranking",
        new ProcesarDatosController(repoEntidad, repoIncidente, repoComunidad)); //cuando haga el post que ya devuelva, un solo controller
  }
}

