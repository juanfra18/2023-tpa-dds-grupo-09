package services.APIs.Georef;

import Config.Config;
import services.Localizacion.ListadoDeDepartamentos;
import services.Localizacion.ListadoDeMunicipios;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Provincia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null;
    private static int maximaCantidadRegistrosDefault = 2500;
    private static final String urlApi = Config.URL_API;
    private Retrofit retrofit;


    private ServicioGeoref() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref instancia(){
        if(instancia== null){
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias();
        Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
        return responseProvinciasArgentinas.body();
    }

    public ListadoDeDepartamentos listadoDeDepartamentos() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeDepartamentos> requestDepartamentosArgentinos = georefService.departamentos("id, nombre, provincia", maximaCantidadRegistrosDefault);
        Response<ListadoDeDepartamentos> responseDepartamentosArgentinos = requestDepartamentosArgentinos.execute();
        return responseDepartamentosArgentinos.body();
    }

    public ListadoDeMunicipios listadoDeMunicipios() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosArgentinos = georefService.municipios("id, nombre, provincia", maximaCantidadRegistrosDefault);
        Response<ListadoDeMunicipios> responseMunicipiosArgentinos = requestMunicipiosArgentinos.execute();
        return responseMunicipiosArgentinos.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(Provincia provincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestListadoDeMunicipios = georefService.municipios(provincia.id, "id, nombre, provincia", maximaCantidadRegistrosDefault);
        Response<ListadoDeMunicipios> responseListadoDeMunicipios = requestListadoDeMunicipios.execute();
        return responseListadoDeMunicipios.body();
    }

    public ListadoDeDepartamentos listadoDeDepartamentosDeProvincia(Provincia provincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeDepartamentos> requestListadoDeDepartamentos = georefService.departamentos(provincia.id, "id, nombre, provincia", maximaCantidadRegistrosDefault);
        Response<ListadoDeDepartamentos> responseListadoDeDepartamentos = requestListadoDeDepartamentos.execute();
        return responseListadoDeDepartamentos.body();
    }
}
