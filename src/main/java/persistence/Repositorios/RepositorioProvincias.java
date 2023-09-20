package persistence.Repositorios;

import services.Localizacion.Provincia;



public class RepositorioProvincias extends RepositorioGenerico<Provincia>{
  private static RepositorioProvincias instancia = null;

  private RepositorioProvincias(){
    super(Provincia.class);
  }

  public static  RepositorioProvincias getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioProvincias();
    }
    return instancia;
  }
}
