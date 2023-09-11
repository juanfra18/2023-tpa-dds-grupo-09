package ServicioAPI;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ObtenerRankingController implements Handler {
  private RepoEntidad repoEntidad;
  private RepoIncidente repoIncidente;
  private RepoComunidad repoComunidad;

  public ObtenerRankingController(RepoEntidad repoEntidad, RepoIncidente repoIncidente, RepoComunidad repoComunidad){
    this.repoEntidad = repoEntidad;
    this.repoIncidente = repoIncidente;
    this.repoComunidad = repoComunidad;
  }

  @Override
  public void handle(Context ctx) throws Exception { //TODO hacer que con los repo responda la request con un JSON conteniendo el listado de las entidades
  }
}

//Ejemplo del seminario: (el ejemplo era un eliminar)

/*
@Override
  public void handle(Context ctx) throws Exception {
    repo.remove(repo.findById(Long.parseLong((ctx.pathParam("id")))));
    ctx.result("Producto eliminado correctamente");
  }
*/


