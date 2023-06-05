package services.APIs.Georef;

import services.Localizacion.ListadoDeDepartamentos;
import services.Localizacion.ListadoDeMunicipios;
import services.Localizacion.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("campos") String campos, @Query("max") int max);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("campos") String campos, @Query("max") int max);
}

