package domain.Persistencia.Repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.ListadoDeMunicipios;
import services.Localizacion.Municipio;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeMunicipios implements WithSimplePersistenceUnit {
  private EntityTransaction tx;
  static ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

  public RepositorioDeMunicipios(){
    this.tx = entityManager().getTransaction();
  }

  public static void main(String[] args) {
    RepositorioProvincias repositorioProvincias = new RepositorioProvincias();
    RepositorioDeMunicipios repositorioDeMunicipios = new RepositorioDeMunicipios();

    ListadoDeMunicipios municipiosDeLaProvincia = servicioGeoref.listadoDeMunicipiosDeProvincia(servicioGeoref.listadoDeProvincias().provinciaDeId(6).get());

    repositorioProvincias.buscarTodos().forEach(
        provincia -> servicioGeoref.listadoDeMunicipiosDeProvincia(provincia).
            getMunicipios().forEach(municipio -> repositorioDeMunicipios.agregar(municipio)));
  }

  public void agregar(Municipio municipio) {
    this.tx.begin();
    entityManager().persist(municipio);
    this.tx.commit();
  }
  public void modificar(Municipio municipio) {
    this.tx.begin();
    entityManager().merge(municipio);
    this.tx.commit();
  }
  public void eliminar(Municipio municipio) {
    this.tx.begin();
    entityManager().remove(municipio);
    this.tx.commit();
  }
  public Municipio buscar(long id){
    return entityManager().find(Municipio.class, id);
  }

  public List<Municipio> buscarTodos(){
    return entityManager().createQuery("from Municipio", Municipio.class).getResultList();
  }

}
