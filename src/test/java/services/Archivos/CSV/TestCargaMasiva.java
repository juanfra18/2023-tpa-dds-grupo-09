package services.Archivos.CSV;

import com.opencsv.exceptions.CsvException;
import domain.Entidades.*;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestCargaMasiva {
  private RepositorioDeEmpresas repo;

  @BeforeEach
  public void init() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
  }

  @Test
  public void testServicios(){
    List<Servicio> servicios = repo.getEmpresas().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0).getEstablecimientos().get(0).getServicios();

    Assertions.assertEquals("ASCENSOR", servicios.get(3).getTipo());
  }

  @Test
  public void testEstablecimientos(){
    List<Establecimiento> establecimientos = repo.getEmpresas().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0).getEstablecimientos();

    Assertions.assertEquals("Rivadavia", establecimientos.get(2).getNombre());
  }

  @Test
  public void testEntidades(){
    List<Entidad> entidades = repo.getEmpresas().get(1).getEntidadesPrestadoras().get(0).getEntidades();

    Assertions.assertEquals("Santander Río Buenos Aires", entidades.get(1).getNombre());
  }

  @Test
  public void testEntidadesPrestadoras(){
    List<EntidadPrestadora> prestadoras = repo.getEmpresas().get(1).getEntidadesPrestadoras();

    Assertions.assertEquals("BCBA", prestadoras.get(1).getNombre());
  }

  @Test
  public void testOrganismosDeControl(){
    List<OrganismoDeControl> organismos = repo.getEmpresas();

    Assertions.assertEquals("BCRA", organismos.get(1).getNombre());
  }

  @Test  //ver si se modifican las entidades dentro una entidad prestadora ya registrada
  public void modificarEmpresaYaExistente(){
    List<Entidad> entidades;
    entidades = repo.getEmpresas().get(1).getEntidadesPrestadoras().get(0).getEntidades();

    Assertions.assertEquals("Santander Río Capital Federal",entidades.get(0).getNombre());
    Assertions.assertEquals("Santander Río Buenos Aires",entidades.get(1).getNombre());
  }
}
