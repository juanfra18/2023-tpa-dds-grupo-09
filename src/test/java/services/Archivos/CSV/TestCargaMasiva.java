package services.Archivos.CSV;

import com.opencsv.exceptions.CsvException;
import domain.Entidades.*;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestCargaMasiva {
  private RepositorioDeEmpresas repo;

  @Test
  public void testServicios() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
    List<Servicio> servicios = repo.getEmpresas().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0).getEstablecimientos().get(0).getServicios();

    Assertions.assertEquals("ASCENSOR", servicios.get(3).getTipo());
  }

  @Test
  public void testEstablecimientos() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
    List<Establecimiento> establecimientos = repo.getEmpresas().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0).getEstablecimientos();

    Assertions.assertEquals("Rivadavia", establecimientos.get(2).getNombre());
  }

  @Test
  public void testEntidades() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
    List<Entidad> entidades = repo.getEmpresas().get(1).getEntidadesPrestadoras().get(0).getEntidades();

    Assertions.assertEquals("Santander Río Buenos Aires", entidades.get(1).getNombre());
  }

  @Test
  public void testEntidadesPrestadoras() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
    List<EntidadPrestadora> prestadoras = repo.getEmpresas().get(1).getEntidadesPrestadoras();

    Assertions.assertEquals("BCBA", prestadoras.get(1).getNombre());
  }

  @Test
  public void testOrganismosDeControl() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
    List<OrganismoDeControl> organismos = repo.getEmpresas();

    Assertions.assertEquals("BCRA", organismos.get(1).getNombre());
  }
}
