package persistence.Repositorios;

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
  public Servicio buscarBanio(TipoBanio tipo){
    return (Servicio) entityManager().createQuery("from Servicio where tipoBanio = :tipo").
            setParameter("tipo",tipo).getResultList().get(0);
  }
  public Servicio buscarElevacion(TipoElevacion tipo){
    return (Servicio) entityManager().createQuery("from Servicio where tipoElevacion = :tipo").
        setParameter("tipo",tipo).getResultList().get(0);
  }
  public List<Servicio> buscarTodos(){
    return entityManager().createQuery("from Servicio", Servicio.class).getResultList();
  }
}
