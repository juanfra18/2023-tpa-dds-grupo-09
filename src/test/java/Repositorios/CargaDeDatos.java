package Repositorios;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.OrganismoDeControl;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.Incidente;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CargaDeDatos {
    List<OrganismoDeControl> empresas;
    CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
    ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
    private List<Entidad> entidades;
    private RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    private RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
    private RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
    private RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
    private RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
    private RepositorioDeUsuarios repositorioDeUsuarios = RepositorioDeUsuarios.getInstancia();
    private RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
    private RepositorioDeMunicipios repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
    private RepositorioProvincias repositorioProvincias = RepositorioProvincias.getInstancia();
    private RepositorioDeEntidadPrestadora repositorioDeEntidadPrestadora = RepositorioDeEntidadPrestadora.getInstancia();
    private RepositorioDeEstablecimientos repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();
    private RepositorioPosicion repositorioPosicion = RepositorioPosicion.getInstancia();
    private RepositorioServicio repositorioServicio = RepositorioServicio.getInstancia();
    private RepositorioDeEmpresas repositorioDeEmpresas = new RepositorioDeEmpresas();
    private RepositorioDeReceptoresDeNotificaciones repositorioDeReceptoresDeNotificaciones = RepositorioDeReceptoresDeNotificaciones.getInstancia();

    private Municipio yavi = new Municipio();
    private Provincia jujuy = new Provincia();
    private ListadoDeProvincias listadoDeProvincias;
    private Servicio banioUnisex;
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
    private ReporteDeIncidente incidenteBanioLineaMitre;

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

            banioUnisex = repositorioServicio.buscar(5);
            lineaMitre = repositorioEntidad.buscar(3);
            estacionRetiro = repositorioDeEstablecimientos.buscar(4);

            //Se cargan las comunidades
            emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();

            comunidad = new Comunidad();
            comunidad.setNombre("Los+Capos");
            comunidad.setEmisorDeNotificaciones(emisorDeNotificaciones);
            comunidad2 = new Comunidad();
            comunidad2.setNombre("Los+Piolas");
            comunidad2.setEmisorDeNotificaciones(emisorDeNotificaciones);

            repositorioComunidad.agregar(comunidad);
            repositorioComunidad.agregar(comunidad2);

            //Se cargan los miembros de comunidad

            pablo = new MiembroDeComunidad();
            pablo.setNombre("pablo");
            pablo.setApellido("perez");
            pablo.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
            pablo.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
            pablo.getReceptorDeNotificaciones().setMail("hola@mail.net");
            pablo.getReceptorDeNotificaciones().setTelefono("+1333333453");

            yavi = repositorioDeMunicipios.buscar(386273);

            pablo.agregarMunicipio(yavi);
            pablo.agregarServicioDeInteres(banioUnisex, Rol.valueOf("AFECTADO"));

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
            incidenteBanioLineaMitre.setServicio(banioUnisex);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,19,30,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioUnisex);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,19,45,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioUnisex);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,21,45,30));
            incidenteBanioLineaMitre.setObservaciones("Baño ya fue limpiado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioUnisex);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,21,50,30));
            incidenteBanioLineaMitre.setObservaciones("Baño ya fue limpiado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioUnisex);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,13,10,45,30));
            incidenteBanioLineaMitre.setObservaciones("Volvieron a mojar el baño");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioUnisex);
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,13,11,0,30));
            incidenteBanioLineaMitre.setObservaciones("Volvieron a mojar el baño");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

            incidenteBanioLineaMitre = new ReporteDeIncidente();
            incidenteBanioLineaMitre.setDenunciante(pablo);
            incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre.setEntidad(lineaMitre);
            incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre.setServicio(banioUnisex);
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