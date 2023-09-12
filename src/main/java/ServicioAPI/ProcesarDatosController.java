package ServicioAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ProcesarDatosController implements Handler {

  private RepoEntidad repoEntidad;
  private RepoIncidente repoIncidente;
  private RepoComunidad repoComunidad;

  public ProcesarDatosController(RepoEntidad repoEntidad, RepoIncidente repoIncidente, RepoComunidad repoComunidad){
    this.repoEntidad = repoEntidad;
    this.repoIncidente = repoIncidente;
    this.repoComunidad = repoComunidad;
  }

  @Override
  public void handle(Context ctx) throws Exception { //TODO hacer que reciba el JSON del body del request y de ahí saque la lista de cada cosa y lo añade a los repos (ver ejemplo del seminario)
    String requestBody = ctx.body();
    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(requestBody, Gson.class);
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
