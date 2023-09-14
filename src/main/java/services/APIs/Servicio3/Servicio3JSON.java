package services.APIs.Servicio3;

import ServicioAPI.JsonRequest;
import com.google.gson.Gson;
import domain.Entidades.Entidad;
import domain.Incidentes.Incidente;
import domain.Personas.Comunidad;

import java.util.List;

public class Servicio3JSON implements Servicio3Adapter{
    @Override
    public void obtenerRanking(List<Entidad> entidades, List<Incidente> incidentes, List<Comunidad> comunidades) {
        JsonRequest jsonRequest = new JsonRequest();
        jsonRequest.setEntidades(entidades);
        jsonRequest.setIncidentes(incidentes);
        jsonRequest.setComunidades(comunidades);

        Gson gson = new Gson();
        String jsonString = gson.toJson(jsonRequest);
        //TODO hacer el request con el JSON
    }
}
