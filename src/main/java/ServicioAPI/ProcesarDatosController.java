package ServicioAPI;

import ServicioAPI.domain.APIComunidad;
import ServicioAPI.domain.APIEntidad;
import ServicioAPI.domain.APIIncidente;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public class ProcesarDatosController implements Handler {
  public ProcesarDatosController(){
    super();
  }

  @Override
  public void handle(Context ctx) throws Exception {
    String requestBody = ctx.body();
    Gson gson = new Gson();
    JsonRequest request = gson.fromJson(requestBody, JsonRequest.class);

    List<APIEntidad> entidades = request.getEntidades();
    List<APIIncidente> incidentes = request.getIncidentes();
    List<APIComunidad> comunidades = request.getComunidades();

    EntidadesConMayorImpacto ranking = new EntidadesConMayorImpacto();
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setEntidades(ranking.armarRanking(entidades,incidentes,comunidades));

    ctx.status(200).result("Datos procesados con éxito").json(gson.toJson(jsonResponse));
  }
}
