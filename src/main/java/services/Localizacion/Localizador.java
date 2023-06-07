package services.Localizacion;

import services.APIs.Georef.ServicioGeoref;

import java.io.IOException;
import java.util.Optional;


public class Localizador {
  public static Localizacion devolverLocalizacion(int id) throws IOException {
    ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

    ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();
    Optional<Provincia> provinciaDeID = listadoDeProvincias.provinciaDeId(id);
    if (provinciaDeID.isPresent()) {
      return provinciaDeID.get();
    }

    ListadoDeDepartamentos listadoDeDepartamentos = servicioGeoref.listadoDeDepartamentos();
    Optional<Departamento> departamentoDeID = listadoDeDepartamentos.departamentoDeId(id);
    if (departamentoDeID.isPresent()) {
      return departamentoDeID.get();
    }

    ListadoDeMunicipios listadoDeMunicipios = servicioGeoref.listadoDeMunicipios();
    Optional<Municipio> municipioDeID = listadoDeMunicipios.municipioDeId(id);
    if (municipioDeID.isPresent()) {
      return municipioDeID.get();
    }

    return null;
  }
}
