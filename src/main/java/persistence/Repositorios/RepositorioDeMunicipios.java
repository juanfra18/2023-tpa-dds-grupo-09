package persistence.Repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.Municipio;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeMunicipios implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  static ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
  private static RepositorioDeMunicipios instancia = null;

  private RepositorioDeMunicipios() {
    tx = entityManager().getTransaction();
  }

  public static  RepositorioDeMunicipios getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeMunicipios();
    }
    return instancia;
  }

  public static void main(String[] args) {
    RepositorioProvincias repositorioProvincias = RepositorioProvincias.getInstancia();
    RepositorioDeMunicipios repositorioDeMunicipios = new RepositorioDeMunicipios();

    repositorioProvincias.buscarTodos().forEach(
        provincia -> servicioGeoref.listadoDeMunicipiosDeProvincia(provincia).
            getMunicipios().forEach(municipio -> repositorioDeMunicipios.agregar(municipio)));
  }

  public void agregar(Municipio municipio) {
    this.tx.begin();
    entityManager().persist(municipio);
    this.tx.commit();
  }
  public void eliminar(Municipio municipio) {
    this.tx.begin();
    entityManager().remove(municipio);
    this.tx.commit();
  }
  public Municipio buscar(int id){
    return entityManager().find(Municipio.class, id);
  }

  public List<Municipio> buscarTodos(){
    return entityManager().createQuery("from Municipio", Municipio.class).getResultList();
  }

}
