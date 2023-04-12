import domain.Seguridad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestSeguridad {

  @Test
  public void contraseniaFacil() throws IOException {
    Seguridad seguridad = new Seguridad();
    seguridad.setContrasenia("ksksksdsdsdsd");
    Assertions.assertTrue(seguridad.validarContrasenia(seguridad.getContrasenia()));
  }


}
