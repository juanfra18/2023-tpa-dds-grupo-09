package controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import models.Config.Config;
import models.domain.Incidentes.EstadoIncidente;
import models.domain.Incidentes.Incidente;
import models.domain.Incidentes.Posicion;
import models.domain.Incidentes.ReporteDeIncidente;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeIncidentes;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SugerenciasDeRevisionController extends ControllerGenerico implements ICrudViewsHandler {

    public void solicitarIncidentes(Context context){
        EntityManager em = EntityManagerSingleton.getInstance();
        Map<String, Object> model = new HashMap<>();
        Usuario usuarioLogueado = super.usuarioLogueado(context,em);
        boolean usuarioBasico = false;
        boolean usuarioEmpresa = false;
        boolean administrador = false;
        MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

        if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
        {
            usuarioBasico = true;
        }
        else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
        {
            usuarioEmpresa = true;
        }
        else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
        {
            administrador = true;
        }

        model.put("miembro_id",miembroDeComunidad.getId());
        model.put("usuarioBasico",usuarioBasico);
        model.put("usuarioEmpresa",usuarioEmpresa);
        model.put("administrador",administrador);
        context.render("SolicitarIncidentes.hbs",model);
        em.close();
    }
    @Override
    public void index(Context context) {
        EntityManager em = EntityManagerSingleton.getInstance();
        Map<String, Object> model = new HashMap<>();
        Usuario usuarioLogueado = super.usuarioLogueado(context,em);
        String latitud = context.pathParam("lat");
        String longitud = context.pathParam("long");
        Posicion posicionUsuario = new Posicion();
        posicionUsuario.setPosicion(latitud+","+longitud);
        RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
        boolean usuarioBasico = false;
        boolean usuarioEmpresa = false;
        boolean administrador = false;
        MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

        if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
        {
            usuarioBasico = true;
        }
        else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
        {
            usuarioEmpresa = true;
        }
        else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
        {
            administrador = true;
        }

        List<Incidente> incidentesCercanos= new ArrayList<>();
        List<Incidente> incidentes= miembroDeComunidad.obtenerIncidentesPorEstado(EstadoIncidente.valueOf("ABIERTO"),
                                    repositorioDeIncidentes.getIncidentesEstaSemana());

        incidentesCercanos = incidentes.stream().filter(i->i.getEstablecimiento().getPosicion()!=null&&
                posicionUsuario.calcularDistancia(i.getEstablecimiento().getPosicion())<=Config.DISTANCIA_MINIMA).toList();

        boolean noHayIncidentes = incidentesCercanos.isEmpty();
        //Se puede pasarle la url para la referencia del js y el css poniendo la url en el config
        model.put("noHayIncidentes",noHayIncidentes);
        model.put("incidentesCercanos",incidentesCercanos);
        model.put("miembro_id",miembroDeComunidad.getId());
        model.put("usuarioBasico",usuarioBasico);
        model.put("usuarioEmpresa",usuarioEmpresa);
        model.put("administrador",administrador);
        context.render("SugerenciasDeRevisionDeIncidentes.hbs",model);
        context.status(200);
        em.close();
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
