import com.opencsv.exceptions.CsvException;
import domain.Servicios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.Archivos.SistemaDeArchivos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCargaMasivaCSV {
  private SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
  private List<OrganismoDeControl> organismos = new ArrayList<>();
  private List<Entidad> entidades = new ArrayList<>();
  static RepositorioDeEntidades repoEntidades;

  @BeforeAll
  public static void init1() throws IOException, CsvException {
    repoEntidades = new RepositorioDeEntidades();
  }

  @Test
  public void testCargarDatosOrganismos() throws IOException, CsvException {
    RepositorioDeOrganismos repo = new RepositorioDeOrganismos();
    Assertions.assertEquals("MESSITEAMO", repo.getOrganismos().get(2).getNombre());
  }

  @Test
  public void testDatosEntidades() {
    Assertions.assertEquals("Tren", repoEntidades.getEntidades().get(1).getNombre());
  }

  @Test
  public void testDatosEstablecimientos() {
    Assertions.assertEquals(TipoEstablecimiento.ESTACION, repoEntidades.getEntidades().get(0).getEstablecimientos().get(0).getTipoEstablecimiento());
    Assertions.assertEquals(TipoEstablecimiento.SUCURSAL, repoEntidades.getEntidades().get(0).getEstablecimientos().get(1).getTipoEstablecimiento());
  }

}