package models.services.APIs.Servicio2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Servicio2Mensajes {
    @POST("/gradoDeConfianza/comunidad")
    Call<Integer> enviarDatosGradoDeConfianzaComunidad(@Body String jsonRequest);
    @POST("/gradoDeConfianza/usuario")
    Call<Integer> enviarDatosGradoDeConfianzaMiembroDeComunidad(@Body String jsonRequest);
}