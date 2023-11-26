package server;

import models.domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import models.domain.Rankings.EntidadesQueSolucionanMasLento;
import models.domain.Rankings.Tierlist;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeIncidentes;
import models.persistence.Repositorios.RepositorioEntidad;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
  public static void main(String[] args) {
    Server.init();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    Tierlist t1 = new EntidadesConMayorCantidadDeIncidentes();
    Tierlist t2 = new EntidadesQueSolucionanMasLento();
    RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();


    //Fecha actual
    LocalDateTime fechaActual = LocalDateTime.now();

    //Tiempo hasta el domingo
    LocalDateTime proximoDomingo = fechaActual.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    Long tiempoFaltante = fechaActual.until(proximoDomingo, ChronoUnit.SECONDS);

    scheduler.scheduleAtFixedRate(() -> {
      EntityManager em = EntityManagerSingleton.getInstance(); //esta bien asi?
      try {
        em.getTransaction().begin();
        t1.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana());
        em.getTransaction().commit();
      } catch (Exception e) {
        em.getTransaction().rollback();
      } finally {
        em.close();
      }
    }, tiempoFaltante, 7 * 24 * 60 * 60, TimeUnit.SECONDS); //Se ejecutara cada domingo

    scheduler.scheduleAtFixedRate(() -> {
      EntityManager em = EntityManagerSingleton.getInstance(); //esta bien asi?
      try {
        em.getTransaction().begin();
          t2.armarRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana());
        em.getTransaction().commit();
      } catch (Exception e) {
        em.getTransaction().rollback();
      } finally {
        em.close();
      }
    }, tiempoFaltante, 7 * 24 * 60 * 60, TimeUnit.SECONDS); //Se ejecutara cada domingo

  }
}
