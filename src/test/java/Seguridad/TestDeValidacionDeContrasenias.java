package Seguridad;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.ValidadorDeContrasenias;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestDeValidacionDeContrasenias {
  ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

  public TestDeValidacionDeContrasenias() throws FileNotFoundException {
  }
  @Test
  public void inicioDeSecionFallido() throws IOException {
    Assertions.assertThrows(InicioDeSesionException.class, () -> {
      Usuario usuario = new Usuario("juan", "LaCasaEnElLago");
    });
  }
  @Test
  public void inicioDeSecionExcitoso() throws IOException {
    Assertions.assertDoesNotThrow(() -> {
      Usuario usuario = new Usuario("juan", "LaCasaEnElLag@");
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
  public void contraseniaConSecuenciaRepetida2() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "HOLASAHOLASA"));
  }

  @Test
  public void contraseniaIgualANombreUsuario() throws IOException {
    Assertions.assertNotEquals("", validador.validarContrasenia("l10N3lMess!", "l10N3lMess!"));
  }
}
