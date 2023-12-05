package models.persistence;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.domain.Entidades.Posicion;
import models.domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import models.domain.Rankings.EntidadesConMayorImpacto;
import models.domain.Rankings.EntidadesQueSolucionanMasLento;
import models.domain.Rankings.Tierlist;
import models.persistence.Repositorios.*;


import javax.persistence.*;


public class Seed2 implements WithSimplePersistenceUnit {
  public static void main(String[] args) {
    EntityManager em = EntityManagerSingleton.getInstance();
    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
    RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();

    try {
      em.getTransaction().begin();

      Tierlist t1 = new EntidadesConMayorCantidadDeIncidentes();
      Tierlist t2 = new EntidadesQueSolucionanMasLento();
      EntidadesConMayorImpacto t3 = new EntidadesConMayorImpacto();

      t1.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana());
      t2.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana());
      //t3.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana(), repositorioComunidad.buscarTodos());

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

