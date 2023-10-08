package models.services.APIs.Servicio3;

import models.Config.Config;
import models.domain.Entidades.Entidad;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class Servicio3JSON implements Servicio3Adapter{
    private static Servicio3JSON instancia = null;
    private static final String urlApi = Config.URL_APIS3;
    private Retrofit retrofit;
    private Servicio3Mensajes servicio3Mensajes;
    private Servicio3JSON() {
        this.retrofit = new Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.servicio3Mensajes = this.retrofit.create(Servicio3Mensajes.class);
    }

    public static Servicio3JSON instancia(){
        if(instancia== null){
            instancia = new Servicio3JSON();
        }
        return instancia;
    }

    public List<Entidad>  obtenerRanking(List<Entidad> entidades, List<Incidente> incidentes, List<Comunidad> comunidades) {
        /*
        JsonRequest jsonRequest = new JsonRequest();
        jsonRequest.setEntidades(entidades);
        jsonRequest.setIncidentes(incidentes);
        jsonRequest.setComunidades(comunidades);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
        Gson gson = gsonBuilder.create();

        try {
            Call<JsonResponse> requestRanking = servicio3.enviarDatosRanking(gson.toJson(jsonRequest));
            Response<JsonResponse> responseRanking = requestRanking.execute();
            return responseRanking.body().getEntidades();
        }
        catch (IOException e)
        {
            throw new NoSePudoConectarConAPI("No se pudo conectar con la API Servicio3");
        }*/
        return new ArrayList<>();
    }
}