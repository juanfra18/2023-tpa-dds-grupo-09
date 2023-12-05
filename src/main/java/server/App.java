package server;

import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import models.domain.Rankings.EntidadesQueSolucionanMasLento;
import models.domain.Rankings.Tierlist;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeIncidentes;
import models.persistence.Repositorios.RepositorioDeReceptoresDeNotificaciones;
import models.persistence.Repositorios.RepositorioEntidad;
import models.persistence.Seed;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
  public static void main(String[] args) {

    Map<String, String> env = System.getenv();
    if (!env.get("SEED_DONE").equals("true")) {
      Seed.seed();
    }

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

    RepositorioDeReceptoresDeNotificaciones repositorioDeReceptoresDeNotificaciones = RepositorioDeReceptoresDeNotificaciones.getInstancia();
    List<ReceptorDeNotificaciones> receptoresDeNotificacionesSinApuro =
        repositorioDeReceptoresDeNotificaciones.buscarTodos().stream().
            filter(receptorDeNotificaciones -> receptorDeNotificaciones.getFormaDeNotificar().getClass().getSimpleName().equals("SinApuros")).toList();


    //Tiempo hasta el proximo dia
    LocalDateTime finDelDia = fechaActual.toLocalDate().atTime(LocalTime.MIDNIGHT);
    Long tiempoHastaProximoDia = fechaActual.until(finDelDia, ChronoUnit.SECONDS);

    scheduler.scheduleAtFixedRate(() -> {
      receptoresDeNotificacionesSinApuro.forEach(receptorDeNotificaciones -> receptorDeNotificaciones.envioProgramado());
    }, tiempoHastaProximoDia, 24 * 60 * 60,TimeUnit.SECONDS);
  }
}

