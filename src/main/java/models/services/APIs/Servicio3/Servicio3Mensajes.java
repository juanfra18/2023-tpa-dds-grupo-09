package models.services.APIs.Servicio3;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Servicio3Mensajes {
    @POST("/")
    Call<String> enviarDatosRanking(@Body String jsonRequest);
}