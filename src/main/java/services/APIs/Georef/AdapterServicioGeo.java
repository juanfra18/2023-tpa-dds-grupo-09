package services.APIs.Georef;

import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import java.io.IOException;

public interface AdapterServicioGeo {
  public Municipio obtenerMunicipio(String nombre) throws IOException;
  public Provincia obtenerProvincia(String nombre) throws IOException;
}
