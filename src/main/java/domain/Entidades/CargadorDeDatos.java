package domain.Entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargadorDeDatos {
  public static List<OrganismoDeControl> cargaDeDatosMASIVA(List<String[]> listaCSV) {
    Map<String, OrganismoDeControl> organismosMap = new HashMap<>();

    for (String[] elemento : listaCSV) {
      String organismoNombre = elemento[0];
      String prestadoraNombre = elemento[1];
      String entidadNombre = elemento[2];
      String entidadTipo = elemento[3];
      String establecimientoNombre = elemento[4];
      String establecimientoTipo = elemento[5];

      OrganismoDeControl organismo = organismosMap.getOrDefault(organismoNombre, new OrganismoDeControl(organismoNombre));
      EntidadPrestadora prestadora = obtenerPrestadora(organismo.getEntidadesPrestadoras(), prestadoraNombre);
      Entidad entidad = obtenerEntidad(prestadora.getEntidades(), entidadNombre, entidadTipo);

      entidad.agregarEstablecimiento(new Establecimiento(establecimientoNombre, establecimientoTipo));
      prestadora.agregarEntidad(entidad);
      organismo.agregarEntidadPrestadora(prestadora);
      organismosMap.put(organismoNombre, organismo);
    }

    return new ArrayList<>(organismosMap.values());
  }

  private static EntidadPrestadora obtenerPrestadora(List<EntidadPrestadora> prestadoras, String nombre) {
    //Devuelve una ya existente o la crea
    List<EntidadPrestadora> repetidos = prestadoras.stream().filter(prestadora -> prestadora.getNombre() == nombre).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    EntidadPrestadora nuevaPrestadora = new EntidadPrestadora(nombre);
    return nuevaPrestadora;
  }

  private static Entidad obtenerEntidad(List<Entidad> entidades, String nombre, String tipoEntidad) {
    //Devuelve una ya existente o la crea
    List<Entidad> repetidos = entidades.stream().filter(entidad -> entidad.getNombre() == nombre).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    Entidad nuevaEntidad = new Entidad(nombre, tipoEntidad);
    return nuevaEntidad;
  }
}
