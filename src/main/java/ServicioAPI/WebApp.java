package ServicioAPI;

import domain.Persistencia.Repositorios.RepositorioDeIncidentes;
import domain.Persistencia.Repositorios.RepositorioEntidad;
import io.javalin.Javalin;

public class WebApp {
  public static void main(String[] args) {
    RepoEntidad repoEntidad = new RepoEntidad();
    RepoIncidente repoIncidente = new RepoIncidente();
    RepoComunidad repoComunidad = new RepoComunidad();
    Integer port = Integer.parseInt(
        System.getProperty("port", "8080"));
    Javalin app = Javalin.create().start(port);
    app.get("/",ctx -> ctx.result("Hola Mundo"));//cuando alguien vaya al path devuelve hola mundo
    app.post("/ranking",
        new PasarDatosController(repoEntidad, repoIncidente, repoComunidad));
    app.get("/ranking",
        new ObtenerRankingController(repoEntidad, repoIncidente, repoComunidad));
  }
}

