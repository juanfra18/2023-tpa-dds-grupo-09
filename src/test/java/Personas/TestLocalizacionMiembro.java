package Personas;

import domain.Personas.MiembroDeComunidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import java.io.IOException;

public class TestLocalizacionMiembro {

  static MiembroDeComunidad miembro;
  public String provincia = "Buenos Aires";
  public String municipio = "General Alvarado";
  static AdapterServicioGeo servicioGeo;

  @BeforeAll
  public static void init() throws IOException {
    miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com");
    servicioGeo = ServicioGeoref.instancia();
  }

  @Test
  public void agregarProvincia() throws IOException {
    Provincia provinciaDeMiembro = servicioGeo.obtenerProvincia(provincia);
    miembro.agregarProvincia(provinciaDeMiembro);
    Assertions.assertEquals(6,miembro.getProvincias().get(0).getId());
  }
  @Test
  public void agregarMunicipio() throws IOException {
    Municipio municipioDeMiembro = servicioGeo.obtenerMunicipio(municipio);
    miembro.agregarMunicipio(municipioDeMiembro);
    Assertions.assertEquals(60280,miembro.getMunicipios().get(0).getId());
  }

}
