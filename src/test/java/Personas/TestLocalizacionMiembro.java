package Personas;

import domain.Incidentes.RepositorioDeIncidentes;
import domain.Personas.MiembroDeComunidad;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.APIs.Georef.AdapterServicioGeo;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLocalizacionMiembro {

  @Mock
  private AdapterServicioGeo servicioGeo;
  private MiembroDeComunidad miembro;
  private String provincia = "Buenos Aires";
  private String municipio = "General Alvarado";
  private RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();

  @BeforeEach
  public void init(){
    miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com","123456789", "CUANDO_SUCEDEN", "WhatsApp",repositorioDeIncidentes);
    MockitoAnnotations.openMocks(this);
    servicioGeo = mock(AdapterServicioGeo.class);
  }

  @Test
  public void agregarProvincia() throws IOException {
    Provincia provinciaDeMiembro = new Provincia(6, "Buenos Aires");
    when(servicioGeo.obtenerProvincia(provincia)).thenReturn(provinciaDeMiembro);

    miembro.agregarProvincia(servicioGeo.obtenerProvincia(provincia));
    Assertions.assertEquals(6, miembro.getProvincias().get(0).getId());
  }
  @Test
  public void agregarMunicipio() throws IOException {
    Municipio municipioDeMiembro = new Municipio(60280, "General Alvarado");
    when(servicioGeo.obtenerMunicipio(municipio)).thenReturn(municipioDeMiembro);

    miembro.agregarMunicipio(servicioGeo.obtenerMunicipio(municipio));
    Assertions.assertEquals(60280, miembro.getMunicipios().get(0).getId());
  }
}
