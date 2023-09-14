package services.APIs.Servicio3;

import Config.Config;
import ServicioAPI.JsonRequest;
import ServicioAPI.JsonResponse;
import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Personas.Comunidad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.APIs.Georef.NoSePudoConectarConAPI;
import services.Localizacion.ListadoDeProvincias;


import java.io.IOException;
import java.util.List;

public class Servicio3JSON {
    private static Servicio3JSON instancia = null;
    private static final String urlApi = Config.URL_APIS3;
    private Retrofit retrofit;
    private Servicio3 servicio3;
    private Servicio3JSON() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.servicio3 = this.retrofit.create(Servicio3.class);
    }

    public static Servicio3JSON instancia(){
        if(instancia== null){
            instancia = new Servicio3JSON();
        }
        return instancia;
    }

    public JsonResponse  obtenerRanking(List<Entidad> entidades, List<Incidente> incidentes, List<Comunidad> comunidades) {
        JsonRequest jsonRequest = new JsonRequest();
        jsonRequest.setEntidades(entidades);
        jsonRequest.setIncidentes(incidentes);
        jsonRequest.setComunidades(comunidades);

        try {
            Call<JsonResponse> requestRanking = servicio3.enviarDatosRanking(jsonRequest);
            Response<JsonResponse> responseRanking = requestRanking.execute();
            return responseRanking.body();
        }
        catch (IOException e)
        {
            throw new NoSePudoConectarConAPI("No se pudo conectar con la API Servicio3");
        }}



}

