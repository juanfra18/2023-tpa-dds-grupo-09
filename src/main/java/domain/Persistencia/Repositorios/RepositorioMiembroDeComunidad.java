package domain.Persistencia.Repositorios;

import domain.Personas.MiembroDeComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioMiembroDeComunidad implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  public RepositorioMiembroDeComunidad(){
     this.tx = entityManager().getTransaction();
  }
  /*
  public static void main(String[] args) {
    RepositorioMiembroDeComunidad repo = new RepositorioMiembroDeComunidad();

    FormaDeNotificar formaDeNotificar = new CuandoSuceden();
    MedioDeComunicacion medioDeComunicacion = new ViaMail();
    RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    ReceptorDeNotificaciones receptorDeNotificaciones = new ReceptorDeNotificaciones();
    receptorDeNotificaciones.setMail("hola@mail.net");
    receptorDeNotificaciones.setTelefono("+1333333453");
    receptorDeNotificaciones.cambiarFormaDeNotificar(formaDeNotificar);
    receptorDeNotificaciones.cambiarMedioDeComunicacion(medioDeComunicacion);

    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    miembroDeComunidad.setNombre("pablo");
    miembroDeComunidad.setApellido("perez");
    miembroDeComunidad.setReceptorDeNotificaciones(receptorDeNotificaciones);
    miembroDeComunidad.setRepositorioDeIncidentes(repositorioDeIncidentes);

    repo.agregar(miembroDeComunidad);

    miembroDeComunidad.setApellido("fernandez");

    repo.modificar(miembroDeComunidad);

    System.out.println(repo.buscar(miembroDeComunidad.getId()).getNombre());

    //repo.eliminar(miembroDeComunidad);

    repo.buscarTodos().forEach(m -> System.out.println(m.getApellido()));
  }
   */
  private void agregar(MiembroDeComunidad miembroDeComunidad) {
    this.tx.begin();
    entityManager().persist(miembroDeComunidad);
    this.tx.commit();
  }
  private void modificar(MiembroDeComunidad miembroDeComunidad) {
    this.tx.begin();
    entityManager().merge(miembroDeComunidad);
    this.tx.commit();
  }
  private void eliminar(MiembroDeComunidad miembroDeComunidad) {
    this.tx.begin();
    entityManager().remove(miembroDeComunidad);
    this.tx.commit();
  }
  private MiembroDeComunidad buscar(long id){
    return entityManager().find(MiembroDeComunidad.class, id);
  }
  private List<MiembroDeComunidad> buscarTodos(){
    return entityManager().createQuery("from MiembroDeComunidad", MiembroDeComunidad.class).getResultList();
  }
}
