package Personas;

import domain.Personas.MiembroDeComunidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestLocalizacionMiembro {
  static MiembroDeComunidad miembro;
  static Localizacion almagro;

  @BeforeAll
  public static void init() throws IOException {
    miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com");
    almagro = Localizador.devolverLocalizacion(2035);
    miembro.agregarLocalizacion(almagro.getId());
  }

  @Test
  public void testearAgregarLocalizacion() {
    Assertions.assertEquals(almagro.getId(), miembro.getLocalizaciones().get(0).getId());
  }
}
