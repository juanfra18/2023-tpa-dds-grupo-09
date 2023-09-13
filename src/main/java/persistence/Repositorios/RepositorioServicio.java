package persistence.Repositorios;

import domain.Incidentes.Incidente;
import domain.Servicios.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioServicio implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  private static RepositorioServicio instancia = null;

  public RepositorioServicio() {
    tx = entityManager().getTransaction();
  }

  public static  RepositorioServicio getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioServicio();
    }
    return instancia;
  }

  public static void main(String[] args) {
    RepositorioServicio repo = new RepositorioServicio();

    Servicio banio = new Banio();
    banio.setTipo(TipoBanio.DAMAS.toString());
    Servicio elevacion = new Elevacion();
    elevacion.setTipo(TipoElevacion.ASCENSOR.toString());

    repo.agregar(banio);
    repo.agregar(elevacion);



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
  public Servicio buscar(long id) {
    return entityManager().find(Servicio.class, id);
  }

  public List<Servicio> buscarTodos(){
    return entityManager().createQuery("from Servicio", Servicio.class).getResultList();
  }
}
