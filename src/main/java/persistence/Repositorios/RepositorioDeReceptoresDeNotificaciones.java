package persistence.Repositorios;

import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeReceptoresDeNotificaciones implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  private static RepositorioDeReceptoresDeNotificaciones instancia = null;

  private RepositorioDeReceptoresDeNotificaciones() {
    tx = entityManager().getTransaction();
  }
  public static  RepositorioDeReceptoresDeNotificaciones getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeReceptoresDeNotificaciones();
    }
    return instancia;
  }

  public static void main(String[] args) {
    RepositorioMiembroDeComunidad repo = RepositorioMiembroDeComunidad.getInstancia();

    FormaDeNotificar formaDeNotificar = new CuandoSuceden();
    MedioDeComunicacion medioDeComunicacion = new ViaMail();
    RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();


    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    miembroDeComunidad.setNombre("pablo");
    miembroDeComunidad.setApellido("perez");
    miembroDeComunidad.setRepositorioDeReportesDeIncidentes(repositorioDeReportesDeIncidentes);
    miembroDeComunidad.getReceptorDeNotificaciones().cambiarFormaDeNotificar(formaDeNotificar);
    miembroDeComunidad.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(medioDeComunicacion);
    miembroDeComunidad.getReceptorDeNotificaciones().setMail("hola@mail.net");
    miembroDeComunidad.getReceptorDeNotificaciones().setTelefono("+1333333453");

    repo.agregar(miembroDeComunidad);
  }

  public void agregar(ReceptorDeNotificaciones receptorDeNotificaciones) {
    this.tx.begin();
    entityManager().persist(receptorDeNotificaciones);
    this.tx.commit();
  }
  public void modificar(ReceptorDeNotificaciones receptorDeNotificaciones) {
    this.tx.begin();
    entityManager().merge(receptorDeNotificaciones);
    this.tx.commit();
  }
  public void eliminar(ReceptorDeNotificaciones receptorDeNotificaciones) {
    this.tx.begin();
    entityManager().remove(receptorDeNotificaciones);
    this.tx.commit();
  }
  public ReceptorDeNotificaciones buscar(long id){
    return entityManager().find(ReceptorDeNotificaciones.class, id);
  }

  public List<ReceptorDeNotificaciones> buscarTodos(){
    return entityManager().createQuery("from ReceptordeNotificaciones", ReceptorDeNotificaciones.class).getResultList();
  }
}
