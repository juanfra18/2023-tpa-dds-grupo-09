package models.persistence;

import models.Config.Config;
import models.domain.Entidades.Entidad;
import models.domain.Entidades.Establecimiento;
import models.domain.Entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import models.domain.Incidentes.Posicion;
import models.domain.Notificaciones.CuandoSuceden;
import models.domain.Notificaciones.FormaDeNotificar;
import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Notificaciones.ViaMail;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import models.domain.Rankings.EntidadesQueSolucionanMasLento;
import models.domain.Rankings.Tierlist;
import models.domain.Usuario.Rol;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.*;
import models.services.APIs.Georef.ServicioGeoref;
import models.services.Archivos.CargadorDeDatos;
import models.services.Archivos.SistemaDeArchivos;
import models.services.Localizacion.ListadoDeProvincias;

import javax.persistence.*;
import java.util.List;


public class Minimain2 implements WithSimplePersistenceUnit {
  public static void main(String[] args) {
    EntityManager em = EntityManagerSingleton.getInstance();
    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();

    try {
      em.getTransaction().begin();

      Tierlist t1 = new EntidadesConMayorCantidadDeIncidentes();
      Tierlist t2 = new EntidadesQueSolucionanMasLento();

      t1.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana());
      t2.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana());

      Posicion posicion = new Posicion();
      posicion.setPosicion("-32.4863144,-58.2299848");

      RepositorioPosicion.getInstancia().agregar(posicion);

      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
  }
}



