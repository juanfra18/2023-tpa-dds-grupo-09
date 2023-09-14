package Personas;

import domain.Notificaciones.FormaDeNotificar;
import domain.Notificaciones.MedioDeComunicacion;
import domain.Personas.MiembroDeComunidad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import persistence.Repositorios.RepositorioDeIncidentes;
import services.APIs.Georef.AdapterServicioGeo;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLocalizacionMiembro {
  @Mock
  private AdapterServicioGeo servicioGeo;
  private MiembroDeComunidad miembro;
  private String provincia = "Buenos Aires";
  private String municipio = "General Alvarado";
  private RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
  private FormaDeNotificar cuandoSuceden;
  private MedioDeComunicacion mail;

  @BeforeEach
  public void init(){
    miembro = new MiembroDeComunidad();
    miembro.setNombre("jose");
    miembro.setApellido("perez");
    miembro.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
    miembro.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
    miembro.getReceptorDeNotificaciones().setMail("perezjose@gmail.com");
    miembro.getReceptorDeNotificaciones().setTelefono("123456789");

    MockitoAnnotations.openMocks(this);
    servicioGeo = mock(AdapterServicioGeo.class);
  }

  @Test
  public void agregarProvincia(){
    Provincia provinciaMockeada = new Provincia();
    provinciaMockeada.setId(6);
    provinciaMockeada.setNombre("Buenos Aires");

    when(servicioGeo.obtenerProvincia(provincia)).thenReturn(provinciaMockeada);

    miembro.agregarProvincia(servicioGeo.obtenerProvincia(provincia));
    Assertions.assertEquals(6, miembro.getProvincias().get(0).getId());
  }
  @Test
  public void agregarMunicipio(){
    Municipio municipioMockeado = new Municipio();
    municipioMockeado.setId(60280);
    municipioMockeado.setNombre("General Alvarado");

    when(servicioGeo.obtenerMunicipio(municipio)).thenReturn(municipioMockeado);

    miembro.agregarMunicipio(servicioGeo.obtenerMunicipio(municipio));
    Assertions.assertEquals(60280, miembro.getMunicipios().get(0).getId());
  }
}
