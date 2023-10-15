package controllers;

import io.javalin.http.Context;
import models.domain.Entidades.Entidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioEntidad;

import javax.persistence.EntityManager;

public class InteresController extends ControllerGenerico{
  RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();

  public void verificarInteresEntidad(Context context){
    EntityManager em = EntityManagerSingleton.getInstance();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    Entidad entidad = repositorioEntidad.buscar(Long.parseLong(context.pathParam("id")));
    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    boolean interes = miembroDeComunidad.esEntidadDeInteres(entidad);
    em.close();
    context.json(interes);
  }
}
