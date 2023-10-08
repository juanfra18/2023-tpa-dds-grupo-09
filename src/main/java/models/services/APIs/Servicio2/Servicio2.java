package models.services.APIs.Servicio2;

import models.Config.Config;
import models.domain.Incidentes.Incidente;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.services.APIs.Servicio2.clases.S2Comunidad;
import models.services.APIs.Servicio2.clases.S2Incidente;
import models.services.APIs.Servicio2.clases.S2Servicio;
import models.services.APIs.Servicio2.clases.S2Usuario;
import models.services.APIs.Servicio3.Servicio3Mensajes;
import models.services.APIs.Servicio3.Servicio3JSON;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Servicio2 implements Servicio2Adapter {
  private static Servicio2 instancia = null;
  private static final String urlApi = Config.URL_APIS2;
  private Retrofit retrofit;
  private Servicio2Mensajes servicio2Mensajes;
  private Servicio2() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    this.servicio2Mensajes = this.retrofit.create(Servicio2Mensajes.class);
  }

  public static Servicio2 instancia(){
    if(instancia== null){
      instancia = new Servicio2();
    }
    return instancia;
  }
  @Override
  public void enviarDatosGradoDeConfianzaComunidad(Comunidad comunidad, List<Incidente> incidentes) {
    S2JsonRequestComunidad request = new S2JsonRequestComunidad();
    S2Comunidad minicomunidad = new S2Comunidad();

    List<S2Usuario> miembros = new ArrayList<>();

    for (MiembroDeComunidad miembroDeComunidad : comunidad.getMiembros()) {
      miembros.add(new S2Usuario(miembroDeComunidad));
    }

    minicomunidad.setGradoDeConfianza(comunidad.getGradosDeConfianza());
    minicomunidad.setMiembros(miembros);

    request.setComunidad(minicomunidad);

    List<S2Incidente> incidentesDeComunidad = new ArrayList<>();

    for (Incidente incidenteDeComunidad : comunidad.getIncidentesDeComunidad(incidentes)){
      S2Incidente incidente = new S2Incidente();

      incidente.setId(incidenteDeComunidad.getId());

      S2Servicio servicio = new S2Servicio();
      servicio.setId(incidenteDeComunidad.getServicio().getId());
      incidente.setServicioAfectado(servicio);

      incidente.setUsuarioReportador(new S2Usuario(incidenteDeComunidad.primeraApertura().getDenunciante()));

      incidente.setFechaApertura(incidenteDeComunidad.primeraApertura().getFechaYhora().toString().substring(0,23)+"Z");

      incidente.setFechaCierre(incidenteDeComunidad.primerCierre().getFechaYhora().toString().substring(0,23)+"Z");

      incidente.setUsuarioAnalizador(new S2Usuario(incidenteDeComunidad.primerCierre().getDenunciante()));

      incidentesDeComunidad.add(incidente);
    }

    //TODO que se haga eso en incidente

    request.setIncidentes(incidentesDeComunidad);

    //TODO hacer el request

  }

  @Override
  public void enviarDatosGradoDeConfianzaMiembroDeComunidad(MiembroDeComunidad miembroDeComunidad) {

  }
  public static void main(String[] args) {
    System.out.println(LocalDateTime.now().toString().substring(0,23)+"Z");
  }
}
