package models.services.APIs.Servicio3;

import com.google.gson.Gson;
import models.Config.Config;
import models.domain.Entidades.Entidad;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import models.services.APIs.Georef.NoSePudoConectarConAPI;
import models.services.APIs.Servicio3.clases.S3Comunidad;
import models.services.APIs.Servicio3.clases.S3Entidad;
import models.services.APIs.Servicio3.clases.S3Incidente;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
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

    public List<Entidad>  obtenerRanking(List<Entidad> entidades, List<Incidente> incidentes, List<Comunidad> comunidades, Long CNF) {
        S3JsonRequest request = new S3JsonRequest();

        List<S3Entidad> miniEntidades = new ArrayList<>();
        entidades.forEach(e -> miniEntidades.add(new S3Entidad(e)));

        List<S3Incidente> miniIncidentes = new ArrayList<>();
        incidentes.forEach(i -> miniIncidentes.add(new S3Incidente(i)));

        List<S3Comunidad> miniComunidades = new ArrayList<>();
        comunidades.forEach(c -> miniComunidades.add(new S3Comunidad(c, incidentes)));

        request.setEntidades(miniEntidades);
        request.setIncidentes(miniIncidentes);
        request.setComunidades(miniComunidades);
        request.setCNF(CNF);

        Gson gson = new Gson();

        try {
            Call<String> requestRanking = servicio3Mensajes.enviarDatosRanking(gson.toJson(request));
            Response<String> responseRanking = requestRanking.execute();
            S3JsonResponse response = gson.fromJson(responseRanking.body(), S3JsonResponse.class);
            List<Entidad> entidadesDevueltas = new ArrayList<>();
            response.getEntidades().forEach(l -> entidadesDevueltas.add(entidades.stream().filter(e -> e.getId().equals(l)).findFirst().get()));
            return entidadesDevueltas;
        }
        catch (IOException e)
        {
            throw new NoSePudoConectarConAPI("No se pudo conectar con la API Servicio3");
        }
    }
}