package Seguridad;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.Seguridad;
import domain.Seguridad.ValidadorDeContrasenias;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestDeValidacionDeContrasenias {
  ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
  Seguridad seguridad = new Seguridad();

  public TestDeValidacionDeContrasenias() throws FileNotFoundException {
  }
  @Test
  public void inicioDeSesionFallido() throws IOException {
    Assertions.assertThrows(InicioDeSesionException.class, () -> {
      Usuario usuario = new Usuario(seguridad, "juan", "LaCasaEnElLago");
    });
  }
  @Test
  public void inicioDeSesionExitoso() throws IOException {
    Assertions.assertDoesNotThrow(() -> {
      Usuario usuario = new Usuario(seguridad, "juan", "LaCasaEnElLag@");
    });
  }
  @Test
  public void contraseniaFacil() throws IOException{
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "1234"));
  }

  @Test
  public void contraseniaSinMayuscula() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "al@ha12345"));
  }

  @Test
  public void contraseniaSinSimbolos() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "aLoHa1212"));
  }

  @Test
  public void contraseniaDeDiccionario() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "elefante"));
  }

  @Test
  public void contraseniaCorta() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "aL@Ha12"));
  }

  @Test
  public void contraseniaConSecuenciaRepetida() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "@Baaaaaaaaaaaaa"));
  }

  @Test
  public void contraseniaValida() throws IOException {
    Assertions.assertEquals("", validador.validarContrasenia("juan", "CasaEnElLag@"));
  }

  @Test
  public void contraseniaConSubSecuenciaRepetida() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "HOLASAHOLASA"));
  }

  @Test
  public void contraseniaIgualANombreUsuario() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("l10N3lMess!", "l10N3lMess!"));
  }
}
