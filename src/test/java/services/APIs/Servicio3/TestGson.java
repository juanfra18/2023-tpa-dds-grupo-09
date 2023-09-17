package services.APIs.Servicio3;

import ServicioAPI.JsonRequest;
import ServicioAPI.domain.*;
import com.google.gson.Gson;
import domain.Entidades.Entidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestGson {
  private Gson gson;
  @BeforeEach
  public void SetUp(){
    this.gson = new Gson();
  }
  @Test
  public void TestGson() {
    JsonRequest jsonRequest = new JsonRequest();

    APIComunidad comunidad1 = new APIComunidad();
      comunidad1.setId(1L);
    APIComunidad comunidad2 = new APIComunidad();
      comunidad2.setId(2L);
    APIComunidad comunidad3 = new APIComunidad();
      comunidad3.setId(3L);

    APIEntidad entidad1 = new APIEntidad();
      entidad1.setId(1L);
    APIEntidad entidad2 = new APIEntidad();
      entidad2.setId(2L);
    APIEntidad entidad3 = new APIEntidad();
      entidad3.setId(3L);

    APIEstablecimiento establecimiento1 = new APIEstablecimiento();
      establecimiento1.setId(1L);
    APIEstablecimiento establecimiento2 = new APIEstablecimiento();
      establecimiento2.setId(2L);
    APIEstablecimiento establecimiento3 = new APIEstablecimiento();
      establecimiento3.setId(3L);

    entidad1.setEstablecimientos(new ArrayList<>(Arrays.asList(establecimiento1)));
    entidad2.setEstablecimientos(new ArrayList<>(Arrays.asList(establecimiento2)));
    entidad3.setEstablecimientos(new ArrayList<>(Arrays.asList(establecimiento3)));

    APIServicio servicio1 = new APIServicio();
      servicio1.setId(1L);
    APIServicio servicio2 = new APIServicio();
      servicio2.setId(2L);
    APIServicio servicio3 = new APIServicio();
      servicio3.setId(3L);
    APIServicio servicio4 = new APIServicio();
      servicio4.setId(4L);
    APIServicio servicio5 = new APIServicio();
      servicio5.setId(5L);
    APIServicio servicio6 = new APIServicio();
      servicio6.setId(6L);
    APIServicio servicio7 = new APIServicio();
      servicio7.setId(7L);

    APIMiembroDeComunidad miembroDeComunidad1 = new APIMiembroDeComunidad();
      miembroDeComunidad1.setId(1L);
      miembroDeComunidad1.setEstablecimientosDeInteres(new ArrayList<>(Arrays.asList(establecimiento1)));
      miembroDeComunidad1.setServiciosQueAfectan(new ArrayList<>(Arrays.asList(servicio1, servicio7)));
    APIMiembroDeComunidad miembroDeComunidad2 = new APIMiembroDeComunidad();
      miembroDeComunidad2.setId(2L);
      miembroDeComunidad2.setEstablecimientosDeInteres(new ArrayList<>(Arrays.asList(establecimiento2)));
      miembroDeComunidad2.setServiciosQueAfectan(new ArrayList<>(Arrays.asList(servicio2)));
    APIMiembroDeComunidad miembroDeComunidad3 = new APIMiembroDeComunidad();
      miembroDeComunidad3.setId(3L);
      miembroDeComunidad3.setEstablecimientosDeInteres(new ArrayList<>(Arrays.asList(establecimiento3)));
      miembroDeComunidad3.setServiciosQueAfectan(new ArrayList<>(Arrays.asList(servicio3)));
    APIMiembroDeComunidad miembroDeComunidad4 = new APIMiembroDeComunidad();
      miembroDeComunidad4.setId(4L);
      miembroDeComunidad4.setEstablecimientosDeInteres(new ArrayList<>(Arrays.asList(establecimiento1, establecimiento3)));
      miembroDeComunidad4.setServiciosQueAfectan(new ArrayList<>(Arrays.asList(servicio4, servicio1)));
    APIMiembroDeComunidad miembroDeComunidad5 = new APIMiembroDeComunidad();
      miembroDeComunidad5.setId(5L);
      miembroDeComunidad5.setEstablecimientosDeInteres(new ArrayList<>(Arrays.asList(establecimiento2, establecimiento3)));
      miembroDeComunidad5.setServiciosQueAfectan(new ArrayList<>(Arrays.asList(servicio5)));
    APIMiembroDeComunidad miembroDeComunidad6 = new APIMiembroDeComunidad();
      miembroDeComunidad6.setId(6L);
      miembroDeComunidad6.setEstablecimientosDeInteres(new ArrayList<>(Arrays.asList(establecimiento1, establecimiento2, establecimiento3)));
      miembroDeComunidad6.setServiciosQueAfectan(new ArrayList<>(Arrays.asList(servicio6)));

    APIIncidente incidente1 = new APIIncidente();
      incidente1.setId(1L);
      incidente1.setServicio(servicio1);
      incidente1.setEstablecimiento(establecimiento1);
      incidente1.setHoraApertura("2023-09-14T13:02:22"); //Formato 'yyyy-MM-ddThh:mm:ss' ISO LOCAL DATE TIME
      incidente1.setHoraCierre("2023-09-14T14:22:17");
    APIIncidente incidente2 = new APIIncidente();
      incidente2.setId(2L);
      incidente2.setServicio(servicio2);
      incidente2.setEstablecimiento(establecimiento2);
      incidente2.setHoraApertura("2023-09-15T13:02:22"); //Formato 'yyyy-MM-ddThh:mm:ss' ISO LOCAL DATE TIME
      incidente2.setHoraCierre("2023-09-15T14:32:17");
    APIIncidente incidente3 = new APIIncidente();
      incidente3.setId(3L);
      incidente3.setServicio(servicio7);
      incidente3.setEstablecimiento(establecimiento3);
      incidente3.setHoraApertura("2023-09-14T13:02:22"); //Formato 'yyyy-MM-ddThh:mm:ss' ISO LOCAL DATE TIME
      incidente3.setHoraCierre("2023-09-15T14:32:17");
    APIIncidente incidente4 = new APIIncidente();
      incidente4.setId(4L);
      incidente4.setServicio(servicio7);
      incidente4.setEstablecimiento(establecimiento3);
      incidente4.setHoraApertura("2023-09-16T13:02:22"); //Formato 'yyyy-MM-ddThh:mm:ss' ISO LOCAL DATE TIME


    comunidad1.setIncidentes(Arrays.asList(incidente3, incidente2));
    comunidad2.setIncidentes(Arrays.asList(incidente2, incidente1));
    comunidad3.setIncidentes(Arrays.asList(incidente1, incidente3, incidente4));

    comunidad1.setMiembros(Arrays.asList(miembroDeComunidad1, miembroDeComunidad2));
    comunidad2.setMiembros(Arrays.asList(miembroDeComunidad3, miembroDeComunidad4));
    comunidad3.setMiembros(Arrays.asList(miembroDeComunidad5, miembroDeComunidad6));

    List<APIEntidad> entidades = new ArrayList<>(Arrays.asList(entidad1, entidad2, entidad3));
    List<APIIncidente> incidentes = new ArrayList<>(Arrays.asList(incidente1, incidente2, incidente3, incidente4));
    List<APIComunidad> comunidades = new ArrayList<>(Arrays.asList(comunidad1, comunidad2, comunidad3));

    jsonRequest.setIncidentes(incidentes);
    jsonRequest.setComunidades(comunidades);
    jsonRequest.setEntidades(entidades);

    System.out.println(gson.toJson(jsonRequest));
  }
}
