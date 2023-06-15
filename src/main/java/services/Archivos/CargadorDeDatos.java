package services.Archivos;

import domain.Entidades.*;
import domain.Servicios.Banio;
import domain.Servicios.Elevacion;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;

import java.util.*;

public class CargadorDeDatos {
  public List<OrganismoDeControl> cargaDeDatosMASIVA(List<String[]> listaCSV){
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

      EntidadPrestadora posiblePrestadora = new EntidadPrestadora(prestadoraNombre);
      Entidad posibleEntidad = new Entidad(entidadNombre, entidadTipo);

      AdapterServicioGeo servicioGeo = ServicioGeoref.instancia(); //Punto de acoplamiento con Georef. Considerar hacer un factory

      Establecimiento posibleEstablecimiento = new Establecimiento(establecimientoNombre, establecimientoTipo, servicioGeo.obtenerMunicipio(establecimientoLocalizacion));

      OrganismoDeControl organismo = organismosMap.getOrDefault(organismoNombre, new OrganismoDeControl(organismoNombre));
      EntidadPrestadora prestadora = obtenerPrestadora(organismo.getEntidadesPrestadoras(), posiblePrestadora);
      Entidad entidad = obtenerEntidad(prestadora.getEntidades(), posibleEntidad);
      Establecimiento establecimiento = obtenerEstablecimiento(entidad.getEstablecimientos(), posibleEstablecimiento);

      if (servicioNombre.equals("Banio")) {
        establecimiento.agregarServicio(new Banio(servicioTipo));
      } else {
        establecimiento.agregarServicio(new Elevacion(servicioTipo));
      }

      entidad.agregarEstablecimiento(establecimiento);
      prestadora.agregarEntidad(entidad);
      organismo.agregarEntidadPrestadora(prestadora);
      organismosMap.put(organismoNombre, organismo);
    }

    return new ArrayList<>(organismosMap.values());
  }

  private EntidadPrestadora obtenerPrestadora(List<EntidadPrestadora> prestadoras, EntidadPrestadora posiblePrestadora) {
    //Devuelve una ya existente o la crea
    Optional<EntidadPrestadora> entidadPrestadora = Optional.ofNullable(prestadoras.stream().filter(prestadora -> prestadora.equals(posiblePrestadora)).toList().get(0));
    return entidadPrestadora.orElseGet(() -> posiblePrestadora);
  }

  private Entidad obtenerEntidad(List<Entidad> entidades, Entidad posibleEntidad) {
    //Devuelve una ya existente o la crea
    Optional<Entidad> entidad = Optional.ofNullable(entidades.stream().filter(entidad1 -> entidad1.equals(posibleEntidad)).toList().get(0));
    return entidad.orElseGet(() -> posibleEntidad);
  }

  private Establecimiento obtenerEstablecimiento(List<Establecimiento> establecimientos, Establecimiento posibleEstablecimiento) {
    Optional<Establecimiento> establecimiento = Optional.ofNullable(establecimientos.stream().filter(establecimiento1 -> establecimiento1.equals(posibleEstablecimiento)).toList().get(0));
    return establecimiento.orElseGet(() -> posibleEstablecimiento);
  }
}
