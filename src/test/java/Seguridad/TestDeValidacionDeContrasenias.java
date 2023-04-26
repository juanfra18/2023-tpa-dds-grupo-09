package Seguridad;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.Seguridad;
import domain.Seguridad.ValidadorDeContrasenias;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDeValidacionDeContrasenias {
  ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
  Seguridad seguridad = new Seguridad();

  @Test
  public void inicioDeSesionFallido(){
    Assertions.assertThrows(InicioDeSesionException.class, () -> {
      Usuario usuario = new Usuario(seguridad, "juan", "LaCasaEnElLago");
    });
  }
  @Test
  public void inicioDeSesionExitoso(){
    Assertions.assertDoesNotThrow(() -> {
      Usuario usuario = new Usuario(seguridad, "juan", "LaCasaEnElLag@");
    });
  }
  @Test
  public void contraseniaFacil(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "1234"));
  }

  @Test
  public void contraseniaSinMayuscula(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "al@ha12345"));
  }

  @Test
  public void contraseniaSinSimbolos(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "aLoHa1212"));
  }

  @Test
  public void contraseniaDeDiccionario(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "elefante"));
  }

  @Test
  public void contraseniaCorta(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "aL@Ha12"));
  }

  @Test
  public void contraseniaConSecuenciaRepetida(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "@Baaaaaaaaaaaaa"));
  }

  @Test
  public void contraseniaValida(){
    Assertions.assertEquals("", validador.validarContrasenia("juan", "CasaEnElLag@"));
  }

  @Test
  public void contraseniaConSubSecuenciaRepetida(){
    Assertions.assertNotEquals("", validador.validarContrasenia("juan", "HOLASAHOLASA"));
  }

  @Test
  public void contraseniaIgualANombreUsuario(){
    Assertions.assertNotEquals("", validador.validarContrasenia("l10N3lMess!", "l10N3lMess!"));
  }
}
