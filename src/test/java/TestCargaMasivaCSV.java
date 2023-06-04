import com.opencsv.exceptions.CsvException;
import domain.Servicios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class TestCargaMasivaCSV {
  static RepositorioDeEmpresas repo;

  @BeforeAll
  public static void init1() throws IOException, CsvException {
    repo = new RepositorioDeEmpresas();
  }

 /* @Test
  public void testCargarDatos() throws IOException, CsvException {
    Assertions.assertEquals("MESSITEAMO", repo.getOrganismos().get(2).getNombre());
  }

  @Test
  public void testDatosEntidades() {
    Assertions.assertEquals("Tren", repo.getEntidades().get(1).getNombre());
  }

  @Test
  public void testDatosEstablecimientos() {
    Assertions.assertEquals(TipoEstablecimiento.ESTACION, repo.getEntidades().get(0).getEstablecimientos().get(0).getTipoEstablecimiento());
    Assertions.assertEquals(TipoEstablecimiento.SUCURSAL, repo.getEntidades().get(0).getEstablecimientos().get(1).getTipoEstablecimiento());
  }
*/
}