package services.Archivos;

import domain.Entidades.*;
import domain.Servicios.Banio;
import domain.Servicios.Elevacion;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;

import java.util.*;

public class CargadorDeDatos {
  public List<OrganismoDeControl> cargaDeDatosMASIVA(List<String[]> listaCSV, AdapterServicioGeo servicioGeo){
    Map<String, OrganismoDeControl> organismosMap = new HashMap<>();

    for (String[] elemento : listaCSV) {
      String organismoNombre = elemento[0];
      String organismoMail = elemento[1];
      String prestadoraNombre = elemento[2];
      String prestadoraMail = elemento[3];
      String entidadNombre = elemento[4];
      String entidadTipo = elemento[5];
      String establecimientoNombre = elemento[6];
      String establecimientoLocalizacion = elemento[7];
      String establecimientoTipo = elemento[8];
      String servicioNombre = elemento[9];
      String servicioTipo = elemento[10];

      EntidadPrestadora posiblePrestadora = new EntidadPrestadora(prestadoraNombre, prestadoraMail);
      Entidad posibleEntidad = new Entidad(entidadNombre, entidadTipo);

      Establecimiento posibleEstablecimiento = new Establecimiento(establecimientoNombre, establecimientoTipo, servicioGeo.obtenerMunicipio(establecimientoLocalizacion));

      OrganismoDeControl organismo = organismosMap.getOrDefault(organismoNombre, new OrganismoDeControl(organismoNombre, organismoMail));
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
    Optional<EntidadPrestadora> entidadPrestadora = prestadoras.stream().filter(prestadora -> prestadora.equals(posiblePrestadora)).toList().stream().findFirst();
    return entidadPrestadora.orElseGet(() -> posiblePrestadora);
  }

  private Entidad obtenerEntidad(List<Entidad> entidades, Entidad posibleEntidad) {
    //Devuelve una ya existente o la crea
    Optional<Entidad> entidad = entidades.stream().filter(entidad1 -> entidad1.equals(posibleEntidad)).toList().stream().findFirst();
    return entidad.orElseGet(() -> posibleEntidad);
  }

  private Establecimiento obtenerEstablecimiento(List<Establecimiento> establecimientos, Establecimiento posibleEstablecimiento) {
    Optional<Establecimiento> establecimiento = establecimientos.stream().filter(establecimiento1 -> establecimiento1.equals(posibleEstablecimiento)).toList().stream().findFirst();
    return establecimiento.orElseGet(() -> posibleEstablecimiento);
  }
}
