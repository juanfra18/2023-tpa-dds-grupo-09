package persistence.Repositorios;

import persistence.EntityManagerSingleton;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityManager;


public class RepositorioProvincias extends RepositorioGenerico<Provincia>{
  private static RepositorioProvincias instancia = null;
  private EntityManager entityManager = EntityManagerSingleton.getInstance();

  private RepositorioProvincias(){
    super(Provincia.class);
  }

  public static  RepositorioProvincias getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioProvincias();
    }
    return instancia;
  }

  @Override
  public Provincia buscar(long id) {
    return entityManager.find(Provincia.class, (int)id);
  }
}
