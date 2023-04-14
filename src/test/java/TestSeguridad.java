import domain.Seguridad;
import domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestSeguridad {

  @Test
  public void contraseniaFacil() throws IOException {
    Seguridad seguridad = new Seguridad();
    Usuario usuario = new Usuario("juan", "contraseña");

    Assertions.assertTrue(seguridad.validarContrasenia(usuario.getContrasenia()));
  }


}
