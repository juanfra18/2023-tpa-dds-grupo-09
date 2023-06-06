package domain.Entidades;

import domain.Servicios.Banio;
import domain.Servicios.Elevacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargadorDeDatos {
  public static List<OrganismoDeControl> cargaDeDatosMASIVA(List<String[]> listaCSV) throws IOException {
    Map<String, OrganismoDeControl> organismosMap = new HashMap<>();

    for (String[] elemento : listaCSV) {
      String organismoNombre = elemento[0];
      String prestadoraNombre = elemento[1];
      String entidadNombre = elemento[2];
      String entidadLocalizacion = elemento[3];
      String entidadTipo = elemento[4];
      String establecimientoNombre = elemento[5];
      String establecimientoLocalizacion = elemento[6];
      String establecimientoTipo = elemento[7];
      String servicioNombre = elemento[8];
      String servicioTipo = elemento[9];

      OrganismoDeControl organismo = organismosMap.getOrDefault(organismoNombre, new OrganismoDeControl(organismoNombre));
      EntidadPrestadora prestadora = obtenerPrestadora(organismo.getEntidadesPrestadoras(), prestadoraNombre);
      Entidad entidad = obtenerEntidad(prestadora.getEntidades(), entidadNombre, entidadTipo, entidadLocalizacion);
      Establecimiento establecimiento = obtenerEstablecimiento(entidad.getEstablecimientos(), establecimientoNombre, establecimientoTipo, establecimientoLocalizacion);

      if (servicioNombre == "Banio") {
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

  private static EntidadPrestadora obtenerPrestadora(List<EntidadPrestadora> prestadoras, String nombre) {
    //Devuelve una ya existente o la crea
    List<EntidadPrestadora> repetidos = prestadoras.stream().filter(prestadora -> prestadora.getNombre() == nombre).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    EntidadPrestadora nuevaPrestadora = new EntidadPrestadora(nombre);
    return nuevaPrestadora;
  }

  private static Entidad obtenerEntidad(List<Entidad> entidades, String nombre, String tipoEntidad, String entidadLocalizacion) throws IOException {
    //Devuelve una ya existente o la crea
    List<Entidad> repetidos = entidades.stream().filter(entidad -> entidad.getNombre() == nombre).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    if (tipoEntidad == "DeEstablecimiento"){
      return new EntidadDeEstablecimiento(nombre, Integer.valueOf(entidadLocalizacion));
    }
    else{
      return new EntidadDeTransporte(nombre, tipoEntidad, Integer.valueOf(entidadLocalizacion));
    }
  }

  private static Establecimiento obtenerEstablecimiento(List<Establecimiento> establecimientos, String establecimientoNombre, String establecimientoTipo, String establecimientoLocalizacion) throws IOException {
    List<Establecimiento> repetidos = establecimientos.stream().filter(establecimiento -> establecimiento.getNombre() == establecimientoNombre).toList();

    if(repetidos.size() != 0){
      return repetidos.get(0);
    }

    return new Establecimiento(establecimientoNombre, establecimientoTipo, Integer.valueOf(establecimientoLocalizacion));
  }
}
