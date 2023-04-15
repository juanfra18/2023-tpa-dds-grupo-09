import domain.Seguridad;
import domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestsDeSeguridad {
  Seguridad seguridad;
  @BeforeEach
  public void init() throws FileNotFoundException {
    seguridad = new Seguridad();
  }
  @Test
  public void contraseniaFacil() throws IOException {
    Usuario usuario = new Usuario("juan", "1234");
    Assertions.assertFalse(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaSinMayuscula() throws IOException {
    Usuario usuario = new Usuario("juan", "aloha1212");
    Assertions.assertFalse(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaSinSimbolos() throws IOException {
    Usuario usuario = new Usuario("juan", "aLoHa1212");
    Assertions.assertFalse(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaDeDiccionario() throws IOException {
    Usuario usuario = new Usuario("juan", "elefante");
    Assertions.assertFalse(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaCorta() throws IOException {
    Usuario usuario = new Usuario("juan", "aL@Ha12");
    Assertions.assertFalse(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaConSecuenciaRepetida() throws IOException {
    Usuario usuario = new Usuario("juan", "@Baaaaaaaaaaaa");
    Assertions.assertFalse(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaValida() throws IOException {
    Usuario usuario = new Usuario("juan", "CasaEnElLag@");
    Assertions.assertTrue(seguridad.validarContrasenia(usuario.getContrasenia()));
  }
  @Test
  public void contraseniaConSecuenciaRepetida2() throws IOException {
    String str = "HOLASAHOLASA";

    Assertions.assertTrue(seguridad.contieneSecuenciasRepetidas2(str));
  }


}
