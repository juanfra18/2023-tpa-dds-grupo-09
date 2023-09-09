package domain.Persistencia.Repositorios;

import domain.Notificaciones.*;
import domain.Servicios.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioServicio implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  public RepositorioServicio(){
    this.tx = entityManager().getTransaction();
  }

  public static void main(String[] args) {
    RepositorioServicio repo = new RepositorioServicio();

    Servicio banio = new Banio();
    banio.setTipo(TipoBanio.UNISEX.toString());
    Servicio elevacion = new Elevacion();
    elevacion.setTipo(TipoElevacion.ESCALERAS_MECANICAS.toString());

    repo.agregar(banio);
    repo.agregar(elevacion);

    banio.setTipo(TipoBanio.CABALLEROS.toString());
    repo.modificar(banio);

    System.out.println(repo.buscarTodos().toString());

    repo.eliminar(banio);

    System.out.println(repo.buscar(banio.getId()));
  }

  public void agregar(Servicio servicio) {
    this.tx.begin();
    entityManager().persist(servicio);
    this.tx.commit();
  }
  public void modificar(Servicio servicio) {
    this.tx.begin();
    entityManager().merge(servicio);
    this.tx.commit();
  }
  public void eliminar(Servicio servicio) {
    this.tx.begin();
    entityManager().remove(servicio);
    this.tx.commit();
  }
  public Servicio buscar(long id){
    return entityManager().find(Servicio.class, id);
  }
  public List<Servicio> buscarTodos(){
    return entityManager().createQuery("from Servicio", Servicio.class).getResultList();
  }
}
