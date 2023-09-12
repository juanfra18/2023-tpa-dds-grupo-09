package persistence.Repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioProvincias implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  static ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

  public RepositorioProvincias(){
    this.tx = entityManager().getTransaction();
  }

  public static void main(String[] args) {
    ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();
    RepositorioProvincias repositorioProvincias = new RepositorioProvincias();
    listadoDeProvinciasArgentinas.getProvincias().forEach(provincia -> repositorioProvincias.agregar(provincia));
  }

  public void agregar(Provincia provincia) {
    this.tx.begin();
    entityManager().persist(provincia);
    this.tx.commit();
  }
  public void modificar(Provincia provincia) {
    this.tx.begin();
    entityManager().merge(provincia);
    this.tx.commit();
  }
  public void eliminar(Provincia provincia) {
    this.tx.begin();
    entityManager().remove(provincia);
    this.tx.commit();
  }
  public Provincia buscar(int id){
    return entityManager().find(Provincia.class, id);
  }

  public List<Provincia> buscarTodos(){
    return entityManager().createQuery("from Provincia", Provincia.class).getResultList();
  }
}
