package services.Archivos;

import domain.Entidades.*;
import domain.Servicios.Banio;
import domain.Servicios.Elevacion;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargadorDeDatos {
  public List<OrganismoDeControl> cargaDeDatosMASIVA(List<String[]> listaCSV) throws IOException {
    Map<String, OrganismoDeControl> organismosMap = new HashMap<>();

    for (String[] elemento : listaCSV) {
      String organismoNombre = elemento[0];
      String prestadoraNombre = elemento[1];
      String entidadNombre = elemento[2];
      String entidadTipo = elemento[3];
      String establecimientoNombre = elemento[4];
      String establecimientoLocalizacion = elemento[5];
      String establecimientoTipo = elemento[6];
      String servicioNombre = elemento[7];
      String servicioTipo = elemento[8];

      OrganismoDeControl organismo = organismosMap.getOrDefault(organismoNombre, new OrganismoDeControl(organismoNombre));
      EntidadPrestadora prestadora = obtenerPrestadora(organismo.getEntidadesPrestadoras(), prestadoraNombre);
      Entidad entidad = obtenerEntidad(prestadora.getEntidades(), entidadNombre, entidadTipo);
      Establecimiento establecimiento = obtenerEstablecimiento(entidad.getEstablecimientos(), establecimientoNombre, establecimientoLocalizacion, establecimientoTipo);

      if (servicioNombre.equals("Banio")) {
        establecimiento.agregarServicio(new Banio(servicioTipo));
      }
      else {
        establecimiento.agregarServicio(new Elevacion(servicioTipo));
      }

      entidad.agregarEstablecimiento(establecimiento);
      prestadora.agregarEntidad(entidad);
      organismo.agregarEntidadPrestadora(prestadora);
      organismosMap.put(organismoNombre, organismo);
    }

    return new ArrayList<>(organismosMap.values());
  }

  private EntidadPrestadora obtenerPrestadora(List<EntidadPrestadora> prestadoras, String nombre) {
    //Devuelve una ya existente o la crea
    List<EntidadPrestadora> repetidos = prestadoras.stream().filter(prestadora -> prestadora.getNombre().equals(nombre)).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    return new EntidadPrestadora(nombre);
  }

  private Entidad obtenerEntidad(List<Entidad> entidades, String nombre, String tipoEntidad) {
    //Devuelve una ya existente o la crea
    List<Entidad> repetidos = entidades.stream().filter(entidad -> entidad.getNombre().equals(nombre)).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    return new Entidad(nombre, tipoEntidad);
  }

  private Establecimiento obtenerEstablecimiento(List<Establecimiento> establecimientos, String establecimientoNombre, String establecimientoLocalizacion, String establecimientoTipo) throws IOException {
    List<Establecimiento> repetidos = establecimientos.stream().filter(establecimiento -> establecimiento.getNombre().equals(establecimientoNombre)).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    //Acoplamiento
    AdapterServicioGeo servicioGeo = ServicioGeoref.instancia();

    return new Establecimiento(establecimientoNombre, establecimientoTipo, servicioGeo.obtenerMunicipio(establecimientoLocalizacion));
  }
}
