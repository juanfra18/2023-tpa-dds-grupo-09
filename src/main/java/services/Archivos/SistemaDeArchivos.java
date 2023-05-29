package services.Archivos;

import Config.Config;
import com.opencsv.exceptions.CsvException;
import domain.Servicios.OrganismoDeControl;
import domain.Servicios.Entidad;
import domain.Servicios.Establecimiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaDeArchivos
{

  public boolean estaEnArchivo(String texto, String ruta) throws IOException {
    String linea = null;
    FileReader fr = null;
    BufferedReader br = null;

    boolean encontrado = false;


    File archivo = null;
    archivo = new File(ruta);
    fr = new FileReader(archivo);
    br = new BufferedReader(fr);
    while ((linea = br.readLine()) != null) {
      if (texto.equals(linea)) encontrado = true;
    }

    if (null != fr) {
      fr.close();
    }

    return encontrado;
  }

  public List<String[]> csvALista(String ruta) throws IOException, CsvException {
    AdapterLectorCSV adapter = new AdapterOpenCSV();
    return adapter.leer(ruta);
  }

  public List<OrganismoDeControl> cargarDatosOrganismos() throws IOException, CsvException {
    List<OrganismoDeControl> organismos = new ArrayList<>();
    List<String[]> lista = this.csvALista(Config.ARCHIVO_CSV_ORGANISMOS);

    for(String[] elemento: lista){
      organismos.add(new OrganismoDeControl(elemento[0]));
    }

    return organismos;
  }
  public List<Entidad> cargarDatosEntidades() throws IOException, CsvException {
    List<Entidad> entidades = new ArrayList<>();
    List<String[]> lista = this.csvALista(Config.ARCHIVO_CSV_ENTIDADES);

    for (String[] elemento : lista) {
      Entidad entidad = new Entidad(elemento[0], elemento[1]);
      if (elemento.length != 2) {
        for (int i = 2; i < elemento.length; i++) {
          String[] arreglo = elemento[i].split(",");
          entidad.agregarEstablecimiento(new Establecimiento(arreglo[0], arreglo[1], arreglo[2]));
        }
      }
      entidades.add(entidad);
    }

    return entidades;
  }
}

