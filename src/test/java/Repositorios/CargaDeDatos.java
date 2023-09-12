package Repositorios;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.OrganismoDeControl;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.Posicion;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.*;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Personas.Rol;
import domain.Servicios.*;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.Repositorios.*;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import java.time.LocalDateTime;
import java.util.List;

public class CargaDeDatos {
    List<OrganismoDeControl> empresas;
    CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
    ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
    private List<Entidad> entidades;
    private RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    private RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
    private RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = new RepositorioMiembroDeComunidad();
    private RepositorioEntidad repositorioEntidad = new RepositorioEntidad();
    private RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = new RepositorioDeReportesDeIncidentes();
    private RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();
    private RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = new RepositorioDeOrganismosDeControl();
    private RepositorioDeMunicipios repositorioDeMunicipios = new RepositorioDeMunicipios();
    private RepositorioProvincias repositorioProvincias = new RepositorioProvincias();
    private RepositorioDeEntidadPrestadora repositorioDeEntidadPrestadora = new RepositorioDeEntidadPrestadora();
    private RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
    private RepositorioPosicion repositorioPosicion = new RepositorioPosicion();
    private RepositorioServicio repositorioServicio = new RepositorioServicio();
    private RepositorioDeEmpresas repositorioDeEmpresas = new RepositorioDeEmpresas();
    private RepositorioDeReceptoresDeNotificaciones repositorioDeReceptoresDeNotificaciones = new RepositorioDeReceptoresDeNotificaciones();

    private Municipio yavi = new Municipio();
    private Provincia jujuy = new Provincia();
    private ListadoDeProvincias listadoDeProvincias;
    private Servicio banioHombres;
    private Servicio banioMujeres;
    private Entidad lineaMitre;
    private Establecimiento estacionRetiro;
    private MiembroDeComunidad pablo;
    private Comunidad comunidad;
    private Comunidad comunidad2;
    private EmisorDeNotificaciones emisorDeNotificaciones;
    private Posicion posicion1;
    private Posicion posicion2;
    private MedioDeComunicacion mail = new ViaMail(); //Ver si que hay que mockear
    private MedioDeComunicacion wpp = new ViaWPP();
    private FormaDeNotificar cuandoSuceden = new CuandoSuceden();
    private FormaDeNotificar sinApuro = new SinApuros();

    private Usuario usuarioPablo = new Usuario();
    private ReporteDeIncidente incidenteBanioLineaMitre = new ReporteDeIncidente();;


    @Test
    public void testBD(){
            //Se cargan las provincias y municipios
            listadoDeProvincias = servicioGeoref.listadoDeProvincias();

            listadoDeProvincias.getProvincias().forEach(provincia -> repositorioProvincias.agregar(provincia));
            repositorioProvincias.buscarTodos().forEach(
                provincia -> servicioGeoref.listadoDeMunicipiosDeProvincia(provincia).
                    getMunicipios().forEach(municipio -> repositorioDeMunicipios.agregar(municipio)));

            //Se cargan las empresas
            empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);
            empresas.forEach(e -> repositorioDeOrganismosDeControl.agregar(e));

            banioHombres = repositorioServicio.buscar(5);
            lineaMitre = repositorioEntidad.buscar(3);
            estacionRetiro = repositorioDeEstablecimientos.buscar(4);

            //Se cargan las comunidades
            emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();

            comunidad = new Comunidad();
            comunidad.setNombre("Los+Capos");
            comunidad.setEmisorDeNotificaciones(emisorDeNotificaciones);
            comunidad.setRepositorioComunidad(new RepositorioComunidad());
            comunidad2 = new Comunidad();
            comunidad2.setNombre("Los+Piolas");
            comunidad2.setEmisorDeNotificaciones(emisorDeNotificaciones);
            comunidad2.setRepositorioComunidad(new RepositorioComunidad());

            repositorioComunidad.agregar(comunidad);
            repositorioComunidad.agregar(comunidad2);

            //Se cargan los miembros de comunidad

            pablo = new MiembroDeComunidad();
            pablo.setNombre("pablo");
            pablo.setApellido("perez");
            pablo.setRepositorioDeReportesDeIncidentes(new RepositorioDeReportesDeIncidentes());
            pablo.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
            pablo.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
            pablo.getReceptorDeNotificaciones().setMail("hola@mail.net");
            pablo.getReceptorDeNotificaciones().setTelefono("+1333333453");

            yavi = repositorioDeMunicipios.buscar(386273);

            pablo.agregarMunicipio(yavi);
            pablo.agregarServicioDeInteres(banioHombres, Rol.valueOf("AFECTADO"));
            pablo.agregarServicioDeInteres(banioMujeres, Rol.valueOf("OBSERVADOR"));

            pablo.unirseAComunidad(repositorioComunidad.buscar(9));
            pablo.unirseAComunidad(repositorioComunidad.buscar(10));

            pablo.agregarEntidadDeInteres(lineaMitre);

            usuarioPablo.setUsername("xPablox");
            usuarioPablo.cambiarContrasenia("pablitoElMa$Cap@DeT@d@s");

            pablo.setUsuario(usuarioPablo);

            repositorioMiembroDeComunidad.agregar(pablo);

            //miembro de comunidad reporta incidentes
            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,19,30,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,19,45,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,21,45,30));
            incidenteBanioLineaMitre.setObservaciones("Baño ya fue limpiado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,21,50,30));
            incidenteBanioLineaMitre.setObservaciones("Baño ya fue limpiado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,13,10,45,30));
            incidenteBanioLineaMitre.setObservaciones("Volvieron a mojar el baño");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,13,11,0,30));
            incidenteBanioLineaMitre.setObservaciones("Volvieron a mojar el baño");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,13,12,0,30));
            incidenteBanioLineaMitre.setObservaciones("El baño sigue mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

/*
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,20,30,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioHombres);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,20,30,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));
*/

      Assertions.assertTrue(true);
    }
}