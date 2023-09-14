package ServicioAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Personas.Comunidad;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

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
  public void handle(Context ctx) throws Exception {
    String requestBody = ctx.body();
    Gson gson = new Gson();
    JsonRequest request = gson.fromJson(requestBody, JsonRequest.class);

    List<Entidad> entidades = request.getEntidades();
    List<Incidente> incidentes = request.getIncidentes();
    List<Comunidad> comunidades = request.getComunidades();

    repoEntidad.addAll(entidades);
    repoIncidente.addAll(incidentes);
    repoComunidad.addAll(comunidades);

    EntidadesConMayorImpacto ranking = new EntidadesConMayorImpacto();
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setEntidades(ranking.armarRanking(entidades,incidentes,comunidades));

    ctx.result(gson.toJson(jsonResponse));
    ctx.status(200).json("Datos procesados con éxito");
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




