package Seguridad;

import domain.Seguridad.RegistroDeUsuarioException;
import domain.Seguridad.ValidadorDeContrasenias;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDeValidacionDeContrasenias {
  ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

  //@Test
  //public void inicioDeSesionFallido() {
  //  Assertions.assertThrows(RegistroDeUsuarioException.class, () -> {
  //    Usuario usuario = new Usuario("juan", "LaCasaEnElLago");
  //  });
  //}

  @Test
  public void inicioDeSesionExitoso() {
    Assertions.assertDoesNotThrow(() -> {
      Usuario usuario = new Usuario("juan", "LaCasaEnElLag@");
    });
  }

  @Test
  public void contraseniaFacil() {
    Assertions.assertThrows(RegistroDeUsuarioException.class, () -> validador.validarContrasenia("1234"));
  }

  @Test
  public void contraseniaSinMayuscula() {
    Assertions.assertThrows(RegistroDeUsuarioException.class, () -> validador.validarContrasenia("al@ha12345"));
  }

  @Test
  public void contraseniaSinSimbolos() {
    Assertions.assertThrows(RegistroDeUsuarioException.class, () -> validador.validarContrasenia("aLoHa1212"));
  }

  @Test
  public void contraseniaDeDiccionario() {
    Assertions.assertThrows(RegistroDeUsuarioException.class, () -> validador.validarContrasenia("elefante"));
  }

  @Test
  public void contraseniaCorta() {
    Assertions.assertThrows(RegistroDeUsuarioException.class, () -> validador.validarContrasenia("aL@Ha12"));
  }

  @Test
  public void contraseniaConSecuenciaRepetida() {
    Assertions.assertThrows(RegistroDeUsuarioException.class, () -> validador.validarContrasenia("a@Baaaaaaaaaaaaa"));
  }

  @Test
  public void contraseniaValida() {
    Assertions.assertDoesNotThrow(() -> validador.validarContrasenia("CasaEnElLag@"));
  }
}
