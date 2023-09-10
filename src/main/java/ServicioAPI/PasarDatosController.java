package ServicioAPI;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class PasarDatosController implements Handler {

  private RepoEntidad repoEntidad;
  private RepoIncidente repoIncidente;
  private RepoComunidad repoComunidad;

  public PasarDatosController(RepoEntidad repoEntidad, RepoIncidente repoIncidente, RepoComunidad repoComunidad){
    this.repoEntidad = repoEntidad;
    this.repoIncidente = repoIncidente;
    this.repoComunidad = repoComunidad;
  }

  @Override
  public void handle(Context ctx) throws Exception { //TODO hacer que reciba el JSON del body del request y de ahí saque la lista de cada cosa y lo añade a los repos (ver ejemplo del seminario)
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
