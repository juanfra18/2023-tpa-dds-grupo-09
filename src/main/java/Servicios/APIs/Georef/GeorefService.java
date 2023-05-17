package Servicios.APIs.Georef;

import Servicios.Localizacion.ListadoDeDepartamentos;
import Servicios.Localizacion.ListadoDeMunicipios;
import Servicios.Localizacion.ListadoDeProvincias;
import Servicios.Localizacion.Provincia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int idProvincia);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int idProvincia, @Query("campos") String campos);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);
}

