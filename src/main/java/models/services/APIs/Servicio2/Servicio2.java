package models.services.APIs.Servicio2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Config.Config;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.services.APIs.Georef.NoSePudoConectarConAPI;
import models.services.APIs.Servicio2.clases.S2Comunidad;
import models.services.APIs.Servicio2.clases.S2Incidente;
import models.services.APIs.Servicio2.clases.S2Servicio;
import models.services.APIs.Servicio2.clases.S2Usuario;
import models.services.APIs.Servicio3.Servicio3Mensajes;
import models.services.APIs.Servicio3.Servicio3JSON;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Servicio2 implements Servicio2Adapter {
  private static Servicio2 instancia = null;
  private static final String urlApi = Config.URL_APIS2;
  private Retrofit retrofit;
  private Servicio2Mensajes servicio2Mensajes;
  private Servicio2() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    this.servicio2Mensajes = this.retrofit.create(Servicio2Mensajes.class);
  }

  public static Servicio2 instancia(){
    if(instancia== null){
      instancia = new Servicio2();
    }
    return instancia;
  }
  @Override
  public Long obtenerGradoDeConfianzaComunidad(Comunidad comunidad, List<Incidente> incidentes) {
    S2JsonRequestComunidad request = new S2JsonRequestComunidad();

    request.setComunidad(new S2Comunidad(comunidad));

    List<S2Incidente> incidentesDeComunidad = new ArrayList<>();

    comunidad.getIncidentesDeComunidad(incidentes).forEach(i -> incidentesDeComunidad.add(new S2Incidente(i)));

    request.setIncidentes(incidentesDeComunidad);

    Gson gson = new Gson();

    try {
      Call<String> requestEnviarDatosGradoDeConfianzaComunidad = servicio2Mensajes.enviarDatosGradoDeConfianzaComunidad(gson.toJson(request));
      Response<String> response = requestEnviarDatosGradoDeConfianzaComunidad.execute();
      S2JsonResponseComunidad jsonResponse = gson.fromJson(response.body(), S2JsonResponseComunidad.class);
      comunidad.setGradosDeConfianza(jsonResponse.getComunidad().getGradoDeConfianza());
      return jsonResponse.getNuevoPuntaje();
    }
    catch (IOException e)
    {
        throw new NoSePudoConectarConAPI("No se pudo conectar con la API Servicio2");
    }

  }

  @Override
  public Long obtenerGradoDeConfianzaMiembroDeComunidad(MiembroDeComunidad miembroDeComunidad, List<Incidente> incidentes) {
    S2JsonRequestUsuario request = new S2JsonRequestUsuario();

    request.setUsuario(new S2Usuario(miembroDeComunidad));

    List<S2Incidente> incidentesRequest = new ArrayList<>();

    incidentes.forEach(i -> incidentesRequest.add(new S2Incidente(i)));

    request.setIncidentes(incidentesRequest);

    Gson gson = new Gson();

    try {
      Call<String> requestEnviarDatosGradoDeConfianzaMiembroDeComunidad = servicio2Mensajes.enviarDatosGradoDeConfianzaMiembroDeComunidad(gson.toJson(request));
      Response<String> response = requestEnviarDatosGradoDeConfianzaMiembroDeComunidad.execute();
      S2JsonResponseUsuario jsonResponse = gson.fromJson(response.body(), S2JsonResponseUsuario.class);
      miembroDeComunidad.setPuntosDeConfianza(jsonResponse.getNuevoPuntaje());
      return jsonResponse.getNuevoPuntaje();
    }
    catch (IOException e)
    {
      throw new NoSePudoConectarConAPI("No se pudo conectar con la API Servicio2");
    }
  }
}
