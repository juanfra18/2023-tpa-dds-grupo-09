package Personas;

import static org.mockito.Mockito.mock;


public class TestInteresesMiembro {
  /*
  @Mock
  private AdapterServicioGeo servicioGeo;
  private MiembroDeComunidad miembro;
  private Municipio generalAlvarado;
  private Municipio pinamar;
  private Servicio banioHombres;
  private Servicio banioMujeres;
  private Establecimiento estacionRetiro;
  private Establecimiento estacionTolosa;
  private Establecimiento estacionPinamar;
  private Entidad lineaMitre;
  private Entidad lineaRoca;
  private RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
  private FormaDeNotificar cuandoSuceden;
  private MedioDeComunicacion mail;
  @BeforeEach
  public void init() throws IOException {
    miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com","123456789", cuandoSuceden, mail,repositorioDeIncidentes);
    MockitoAnnotations.openMocks(this);
    servicioGeo = mock(AdapterServicioGeo.class);
    when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
    when(servicioGeo.obtenerMunicipio("Pinamar")).thenReturn(pinamar);

    banioHombres = new Banio("CABALLEROS");
    banioMujeres = new Banio("DAMAS");

    miembro.agregarMunicipio(pinamar);
    miembro.agregarServicioDeInteres(banioHombres, Rol.valueOf("AFECTADO"));

    lineaMitre = new Entidad("Linea Mitre","FERROCARRIL");

    estacionRetiro = new Establecimiento("Retiro","ESTACION", generalAlvarado);
    estacionRetiro.agregarServicio(banioHombres);

    estacionPinamar = new Establecimiento("Pinamar", "ESTACION", pinamar);
    estacionPinamar.agregarServicio(banioHombres);

    lineaMitre.agregarEstablecimiento(estacionRetiro);
    lineaMitre.agregarEstablecimiento(estacionPinamar);

    lineaRoca = new Entidad("Linea Roca","FERROCARRIL");
    lineaRoca.agregarEstablecimiento(estacionTolosa);

    estacionTolosa = new Establecimiento("Tolosa","ESTACION", generalAlvarado);
    estacionTolosa.agregarServicio(banioMujeres);
  }

  @Test
  public void leInteresaElServicioYElEstablecimiento() throws IOException {
    miembro.agregarEntidadDeInteres(lineaMitre);
    Mockito.when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
    Assertions.assertTrue(miembro.tieneInteres(banioHombres, estacionRetiro));
  }

  @Test
  public void noLeInteresaElServicio() throws IOException {
    miembro.agregarEntidadDeInteres(lineaRoca);
    Mockito.when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
    Assertions.assertFalse(miembro.tieneInteres(banioMujeres, estacionTolosa));
  }

  @Test
  public void leInteresaElServicioPeroNoElEstablecimiento() throws IOException {
    Mockito.when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
    Assertions.assertFalse(miembro.tieneInteres(banioHombres, estacionRetiro));
  }

  @Test
  public void leInteresaPeroNoQuedaCerca(){
    miembro.agregarEntidadDeInteres(lineaMitre);
    Assertions.assertTrue(miembro.tieneInteres(banioHombres,estacionPinamar));
  }

  @Test
  public void CambiarRolDeServicioDeInters(){
    ParServicioRol banioYrol = miembro.getServiciosDeInteres().stream().filter(parServicioRol -> parServicioRol.getServicio().equals(banioHombres)).findFirst().get();
    Assertions.assertEquals("AFECTADO",banioYrol.getRol().toString());
    miembro.cambiarRolSobreServicio(banioHombres);
    Assertions.assertEquals("OBSERVADOR",banioYrol.getRol().toString());
  }

   */
}



