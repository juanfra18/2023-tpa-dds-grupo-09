package services.Archivos.CSV;

public class TestCargaMasiva {
  /*
  List<OrganismoDeControl> empresas;
  CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
  SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();

  @BeforeEach
  public void init(){
    CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    AdapterServicioGeo servicioGeo = ServicioGeoref.instancia(); //punto de acoplamiento con georef
    repo = new RepositorioDeEmpresas(cargadorDeDatos, servicioGeo);
  }

  @Test
  public void testServicios(){
    List<Servicio> servicios = repo.getEmpresas().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0).getEstablecimientos().get(0).getServicios();
    Assertions.assertEquals("Banio UNISEX", servicios.get(0).getTipo());
  }

  @Test
  public void testEstablecimientos(){
    List<Establecimiento> establecimientos = repo.getEmpresas().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0).getEstablecimientos();

    Assertions.assertEquals("Retiro", establecimientos.get(0).getNombre());
  }

  @Test
  public void testEntidades(){
    List<Entidad> entidades = repo.getEmpresas().get(1).getEntidadesPrestadoras().get(0).getEntidades();

    Assertions.assertEquals("Santander Río Buenos Aires", entidades.get(1).getNombre());
  }

  @Test
  public void testEntidadesPrestadoras(){
    List<EntidadPrestadora> prestadoras = repo.getEmpresas().get(1).getEntidadesPrestadoras();

    Assertions.assertEquals("BCBA", prestadoras.get(1).getNombre());
  }

  @Test
  public void testOrganismosDeControl(){
    List<OrganismoDeControl> organismos = repo.getEmpresas();

    Assertions.assertEquals("BCRA", organismos.get(1).getNombre());
  }

  @Test  //ver si se modifican las entidades dentro una entidad prestadora ya registrada
  public void modificarEmpresaYaExistente(){
    List<Entidad> entidades;
    entidades = repo.getEmpresas().get(1).getEntidadesPrestadoras().get(0).getEntidades();

    Assertions.assertEquals("Santander Río Capital Federal",entidades.get(0).getNombre());
    Assertions.assertEquals("Santander Río Buenos Aires",entidades.get(1).getNombre());
  }

   */
}
