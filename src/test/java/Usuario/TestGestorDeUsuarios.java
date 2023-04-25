package Usuario;

import domain.Seguridad.InicioDeSesionException;
import domain.Usuario.GestorDeUsuarios;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class TestGestorDeUsuarios {
  GestorDeUsuarios gestor;

  @Test
  public void testGestorContraseniaDebil() throws FileNotFoundException, InicioDeSesionException {
    gestor = new GestorDeUsuarios();
    Assertions.assertThrows(InicioDeSesionException.class, () -> {
      gestor.crearUsuario("juan", "hola1234");
    });
  }

  @Test
  public void testGestorContraseniaFuerte() throws InicioDeSesionException, FileNotFoundException {
    gestor = new GestorDeUsuarios();
    Assertions.assertDoesNotThrow(() -> {
      gestor.crearUsuario("juan", "M@1kaolv39#");
    });
  }
}
