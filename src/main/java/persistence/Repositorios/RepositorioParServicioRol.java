package persistence.Repositorios;

import domain.Personas.ParServicioRol;
import domain.Personas.Rol;
import domain.Servicios.Banio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioParServicioRol implements WithSimplePersistenceUnit {
  private EntityTransaction tx;

  public RepositorioParServicioRol(){
    this.tx = entityManager().getTransaction();
  }

  public static void main(String[] args) {
    RepositorioParServicioRol repositorioParServicioRol = new RepositorioParServicioRol();
    Banio banio = new Banio();
    banio.setTipo("CABALLEROS");
    RepositorioServicio repositorioServicio = new RepositorioServicio();
    repositorioServicio.agregar(banio);
    Banio banio2 = new Banio();
    banio2.setTipo("CABALLEROS");
    ParServicioRol par = new ParServicioRol();
    par.setRol(Rol.OBSERVADOR);
    par.setServicio(banio2);
    repositorioParServicioRol.agregar(par);
  }

  public void agregar(ParServicioRol parServicioRol) {
    this.tx.begin();
    entityManager().persist(parServicioRol);
    this.tx.commit();
  }
  public void modificar(ParServicioRol parServicioRol) {
    this.tx.begin();
    entityManager().merge(parServicioRol);
    this.tx.commit();
  }
  public void eliminar(ParServicioRol parServicioRol) {
    this.tx.begin();
    entityManager().remove(parServicioRol);
    this.tx.commit();
  }
  public ParServicioRol buscar(long id){
    return entityManager().find(ParServicioRol.class, id);
  }
  public List<ParServicioRol> buscarTodos(){
    return entityManager().createQuery("from ParServicioRol", ParServicioRol.class).getResultList();
  }
}
