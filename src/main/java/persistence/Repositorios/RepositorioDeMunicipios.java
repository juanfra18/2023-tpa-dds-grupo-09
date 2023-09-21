package persistence.Repositorios;

import persistence.EntityManagerSingleton;
import services.Localizacion.Municipio;

import javax.persistence.EntityManager;


public class RepositorioDeMunicipios extends RepositorioGenerico<Municipio> {
  private static RepositorioDeMunicipios instancia = null;
  private EntityManager entityManager = EntityManagerSingleton.getInstance();
  private RepositorioDeMunicipios() {
    super(Municipio.class);
  }

  public static  RepositorioDeMunicipios getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeMunicipios();
    }
    return instancia;
  }

  @Override
  public Municipio buscar(long id) {
    return entityManager.find(Municipio.class, (int)id);
  }
}
