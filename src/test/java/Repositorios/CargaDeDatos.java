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
import domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import domain.Rankings.EntidadesQueSolucionanMasLento;
import domain.Servicios.*;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import persistence.Repositorios.*;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CargaDeDatos {
    private List<OrganismoDeControl> empresas;
    private CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    private SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
    private ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
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
    private RepositorioDeEmpresas repositorioDeEmpresas = RepositorioDeEmpresas.getInstancia();
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
    private EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
    private EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
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

            ReporteDeIncidente incidenteBanioLineaMitre1 = new ReporteDeIncidente();
            incidenteBanioLineaMitre1.setDenunciante(pablo);
            incidenteBanioLineaMitre1.setClasificacion(EstadoIncidente.ABIERTO);
            incidenteBanioLineaMitre1.setEntidad(lineaMitre);
            incidenteBanioLineaMitre1.setEstablecimiento(estacionRetiro);
            incidenteBanioLineaMitre1.setServicio(banioUnisex);
            incidenteBanioLineaMitre1.setFechaYhora(LocalDateTime.of(2023,9,12,19,30,30));
            incidenteBanioLineaMitre1.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre1,pablo.getComunidades().get(0));



            /*
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
            incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,12,19,45,30));
            incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

            pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));
             */

            Assertions.assertTrue(true);
        }

        @Test
        public void rankingSolucionanMasLento() {
            entidadesQueSolucionanMasLento.armarRanking(repositorioEntidad.buscarTodos(),repositorioDeIncidentes.getIncidentesEstaSemana());
        }

        @Test
        public void rankingMayorCantidadDeIncidentes() {
            entidadesConMayorCantidadDeIncidentes.armarRanking(repositorioEntidad.buscarTodos(),repositorioDeIncidentes.getIncidentesEstaSemana());
        }

        @Test
        public void solicitarInformacionDeIncidentesAbiertos(){
            MiembroDeComunidad pablito = repositorioMiembroDeComunidad.buscar(11);

            Assertions.assertEquals(1,pablito.obtenerIncidentesPorEstado(EstadoIncidente.ABIERTO).size());

        }
        @Test
        public void solicitarInformacionDeIncidentesCerrados(){
            MiembroDeComunidad pablito = repositorioMiembroDeComunidad.buscar(11);

            Assertions.assertEquals(0,pablito.obtenerIncidentesPorEstado(EstadoIncidente.CERRADO).size());
        }

    @Test
    public void recibirInformacion() {
        emisorDeNotificaciones.generarRankings();
    }
    @Test
    public void IncidentesDeLaSemana(){
        Assertions.assertEquals(2,repositorioDeIncidentes.getIncidentesEstaSemana().size());
        List<Incidente> i = repositorioDeIncidentes.getIncidentesEstaSemana();
        for(Incidente incidente : i)
        {
            System.out.println(incidente.mensaje());
        }

    }

/*
    @Test
    public void ReportarUnIncidente(){
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(0));

        Assertions.assertEquals(incidenteBanioHombre.getObservaciones(),pablo.getComunidades().get(0).getIncidentesDeLaComunidad().get(0).getReportesDeApertura().get(0).getObservaciones());
        Assertions.assertEquals(incidenteBanioHombre.getServicio(),pablo.getComunidades().get(0).getIncidentesDeLaComunidad().get(0).getServicio());
        Assertions.assertEquals(incidenteBanioHombre.getEstablecimiento(),pablo.getComunidades().get(0).getIncidentesDeLaComunidad().get(0).getEstablecimiento());

        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(1));

        Assertions.assertEquals(incidenteBanioHombre.getObservaciones(),pablo.getComunidades().get(1).getIncidentesDeLaComunidad().get(0).getReportesDeApertura().get(0).getObservaciones());
        Assertions.assertEquals(incidenteBanioHombre.getServicio(),pablo.getComunidades().get(1).getIncidentesDeLaComunidad().get(0).getServicio());
        Assertions.assertEquals(incidenteBanioHombre.getEstablecimiento(),pablo.getComunidades().get(1).getIncidentesDeLaComunidad().get(0).getEstablecimiento());
    }

    @Test
    public void DistanciasCercanas(){
        posicion1 = new Posicion("100,100");
        posicion2 = new Posicion("50,50");
        Assertions.assertTrue(posicion1.distancia(posicion2) <= Config.DISTANCIA_MINIMA);
    }

    @Test
    public void peticionDeRevision(){
        List<Comunidad> comunidades = new ArrayList<>();
        EmisorDeNotificaciones nuevoEmisor = EmisorDeNotificaciones.getInstancia();
        pablo = mock(MiembroDeComunidad.class);
        julieta = mock(MiembroDeComunidad.class);
        maria = mock(MiembroDeComunidad.class);
        Comunidad comunidadMock = new Comunidad("Los+Mockeados", nuevoEmisor);
        when(pablo.validarSolicitudDeRevision(incidenteBanioHombre)).thenReturn(true);
        when(julieta.validarSolicitudDeRevision(incidenteBanioHombre)).thenReturn(true);
        when(maria.validarSolicitudDeRevision(incidenteBanioHombre)).thenReturn(true);
        comunidadMock.agregarMiembro(pablo); comunidadMock.agregarMiembro(julieta); comunidadMock.agregarMiembro(maria);
        comunidades.add(comunidadMock);
        nuevoEmisor.solicitarRevisionDeIncidente(comunidades); //no se me ocurre como mockear la posicion, puse que devuelva true a la solicitud en miembro y manda el mail
    }
    */
}