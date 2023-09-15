package services.APIs.Georef;

import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

public interface AdapterServicioGeo {
  public Municipio obtenerMunicipio(String nombre);
  public Provincia obtenerProvincia(String nombre);
}
