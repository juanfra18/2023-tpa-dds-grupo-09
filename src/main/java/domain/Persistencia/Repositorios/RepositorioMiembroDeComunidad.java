package domain.Persistencia.Repositorios;

import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import domain.Personas.ParServicioRol;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioMiembroDeComunidad implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  RepositorioParServicioRol repositorioParServicioRol;
  public RepositorioMiembroDeComunidad(){
     this.tx = entityManager().getTransaction();
     repositorioParServicioRol = new RepositorioParServicioRol();
  }

  public static void main(String[] args) {
    RepositorioMiembroDeComunidad repo = new RepositorioMiembroDeComunidad();

    FormaDeNotificar formaDeNotificar = new CuandoSuceden();
    MedioDeComunicacion medioDeComunicacion = new ViaMail();
    RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = new RepositorioDeReportesDeIncidentes();


    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    miembroDeComunidad.setNombre("pablo");
    miembroDeComunidad.setApellido("perez");
    miembroDeComunidad.setRepositorioDeReportesDeIncidentes(repositorioDeReportesDeIncidentes);
    miembroDeComunidad.getReceptorDeNotificaciones().cambiarFormaDeNotificar(formaDeNotificar);
    miembroDeComunidad.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(medioDeComunicacion);
    miembroDeComunidad.getReceptorDeNotificaciones().setMail("hola@mail.net");
    miembroDeComunidad.getReceptorDeNotificaciones().setTelefono("+1333333453");

    repo.agregar(miembroDeComunidad);

    miembroDeComunidad.setApellido("fernandez");

    repo.modificar(miembroDeComunidad);

    System.out.println(repo.buscar(miembroDeComunidad.getId()).getNombre());

    //repo.eliminar(miembroDeComunidad);

    repo.buscarTodos().forEach(m -> System.out.println(m.getApellido()));
  }

  public void agregar(MiembroDeComunidad miembroDeComunidad) {
    this.tx.begin();
    entityManager().persist(miembroDeComunidad);
    this.tx.commit();
  }
  public void modificar(MiembroDeComunidad miembroDeComunidad) {
    this.tx.begin();
    entityManager().merge(miembroDeComunidad);
    this.tx.commit();
  }
  public void eliminar(MiembroDeComunidad miembroDeComunidad) {
    this.tx.begin();
    entityManager().remove(miembroDeComunidad);
    this.tx.commit();
  }
  public MiembroDeComunidad buscar(long id){
    return entityManager().find(MiembroDeComunidad.class, id);
  }
  public List<MiembroDeComunidad> buscarTodos(){
    return entityManager().createQuery("from MiembroDeComunidad", MiembroDeComunidad.class).getResultList();
  }
}
