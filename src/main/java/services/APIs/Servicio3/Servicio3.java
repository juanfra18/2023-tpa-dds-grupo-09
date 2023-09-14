package services.APIs.Servicio3;

import ServicioAPI.JsonRequest;
import ServicioAPI.JsonResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Servicio3 {
    @POST("/ranking")
    Call<JsonResponse> enviarDatosRanking(@Body String jsonRequest);
}