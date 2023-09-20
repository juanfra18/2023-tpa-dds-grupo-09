package persistence.Repositorios;

import services.Localizacion.Municipio;


public class RepositorioDeMunicipios extends RepositorioGenerico<Municipio> {
  private static RepositorioDeMunicipios instancia = null;

  private RepositorioDeMunicipios() {
    super(Municipio.class);
  }

  public static  RepositorioDeMunicipios getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeMunicipios();
    }
    return instancia;
  }
}
