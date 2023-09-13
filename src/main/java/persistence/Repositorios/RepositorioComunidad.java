package persistence.Repositorios;

import domain.Notificaciones.*;
import domain.Personas.Comunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioComunidad implements WithSimplePersistenceUnit {

  private EntityTransaction tx;
  private static RepositorioComunidad instancia = null;

  private RepositorioComunidad() {
    tx = entityManager().getTransaction();
  }

  public static  RepositorioComunidad getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioComunidad();
    }
    return instancia;
  }
  public static void main(String[] args) {
    RepositorioComunidad repo = new RepositorioComunidad();
    Comunidad comunidad = new Comunidad();
    comunidad.setNombre("comunidadcita");
    comunidad.setEmisorDeNotificaciones(EmisorDeNotificaciones.getInstancia());

    repo.agregar(comunidad);

    comunidad.setNombre("Xx__messilovers__xX");

    repo.modificar(comunidad);

    System.out.println(repo.buscar(comunidad.getId()).getNombre());

    System.out.println(repo.buscarTodos().size());

    //repo.eliminar(comunidad);
  }
  public void agregar(Comunidad comunidad) {
    this.tx.begin();
    entityManager().persist(comunidad);
    this.tx.commit();
  }
  public void modificar(Comunidad comunidad) {
    this.tx.begin();
    entityManager().merge(comunidad);
    this.tx.commit();
  }
  public void eliminar(Comunidad comunidad) {
    this.tx.begin();
    entityManager().remove(comunidad);
    this.tx.commit();
  }
  public Comunidad buscar(long id){
    return entityManager().find(Comunidad.class, id);
  }
  public List<Comunidad> buscarTodos(){
    return entityManager().createQuery("from Comunidad", Comunidad.class).getResultList();
  }
}
