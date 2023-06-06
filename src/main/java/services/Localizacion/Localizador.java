package services.Localizacion;

import services.APIs.Georef.ServicioGeoref;

import java.io.IOException;


public class Localizador {
  public static Localizacion devolverLocalizacion(int id) throws IOException {
    ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
    ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();
    if(listadoDeProvincias.provincias.stream().anyMatch(provincia -> provincia.getId() == id)){
      return listadoDeProvincias.provinciaDeId(id).get();
    }

    ListadoDeDepartamentos listadoDeDepartamentos = servicioGeoref.listadoDeDepartamentos();
    if(listadoDeDepartamentos.departamentos.stream().anyMatch(depto -> depto.getId() == id)){
      return listadoDeDepartamentos.departamentoDeId(id).get();
    }

    return servicioGeoref.listadoDeMunicipios().municipioDeId(id).get();
  }
}
