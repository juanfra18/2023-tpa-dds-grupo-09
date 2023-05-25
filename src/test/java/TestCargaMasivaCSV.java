import Config.Config;
import com.opencsv.exceptions.CsvException;
import domain.Personas.OrganismoDeControl;
import domain.Servicios.Entidad;
import org.junit.jupiter.api.Test;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCargaMasivaCSV {
  private SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
  private List<OrganismoDeControl> organismos = new ArrayList<>();
  private List<Entidad> entidades = new ArrayList<>();
  @Test
  public void cargarDatosOrganismos() throws IOException, CsvException {
    List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ORGANISMOS);

    for(String[] elemento: lista){
      organismos.add(new OrganismoDeControl(elemento[0]));
    }
  }
  @Test
  public void cargarDatosEntidades() throws IOException, CsvException {
    List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ENTIDADES);

    for(String[] elemento: lista){
      entidades.add(new Entidad(elemento[0], elemento[1]));
    }
  }
}
