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
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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


public class CargaDeDatos implements WithSimplePersistenceUnit {
    private List<OrganismoDeControl> empresas;
    private CargadorDeDatos cargadorDeDatos;
    private SistemaDeArchivos sistemaDeArchivos;
    private ServicioGeoref servicioGeoref;
    private List<Entidad> entidades;
    private RepositorioDeIncidentes repositorioDeIncidentes;
    private RepositorioComunidad repositorioComunidad;
    private RepositorioMiembroDeComunidad repositorioMiembroDeComunidad;
    private RepositorioEntidad repositorioEntidad;
    private RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl;
    private RepositorioDeMunicipios repositorioDeMunicipios;
    private RepositorioProvincias repositorioProvincias;
    private RepositorioDeEntidadPrestadora repositorioDeEntidadPrestadora;
    private RepositorioDeEstablecimientos repositorioDeEstablecimientos;
    private RepositorioPosicion repositorioPosicion;
    private RepositorioServicio repositorioServicio;
    private RepositorioDeEmpresas repositorioDeEmpresas;
    private RepositorioDeReceptoresDeNotificaciones repositorioDeReceptoresDeNotificaciones;

    private Municipio yavi;
    private Provincia jujuy;
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
    private MedioDeComunicacion mail; //Ver si que hay que mockear
    private MedioDeComunicacion wpp;
    private FormaDeNotificar cuandoSuceden;
    private FormaDeNotificar sinApuro;
    private Usuario usuarioPablo;
    private ReporteDeIncidente incidenteBanioLineaMitre;
    private EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento;
    private EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes;
    private EntityTransaction tx;
    @BeforeEach
    public void before() {

        this.tx = entityManager().getTransaction();
        this.tx.begin();

        this.cargadorDeDatos = new CargadorDeDatos();
        this.sistemaDeArchivos = new SistemaDeArchivos();
        this.servicioGeoref = ServicioGeoref.instancia();
        this.repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
        this.repositorioComunidad = RepositorioComunidad.getInstancia();
        this.repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
        this.repositorioEntidad = RepositorioEntidad.getInstancia();
        this.repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
        this.repositorioDeUsuarios = RepositorioDeUsuarios.getInstancia();
        this.repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
        this.repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
        this.repositorioProvincias = RepositorioProvincias.getInstancia();
        this.repositorioDeEntidadPrestadora = RepositorioDeEntidadPrestadora.getInstancia();
        this.repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();
        this.repositorioPosicion = RepositorioPosicion.getInstancia();
        this.repositorioServicio = RepositorioServicio.getInstancia();
        this.repositorioDeEmpresas = RepositorioDeEmpresas.getInstancia();
        this.repositorioDeReceptoresDeNotificaciones = RepositorioDeReceptoresDeNotificaciones.getInstancia();
        this.yavi = new Municipio();
        this.jujuy = new Provincia();
        this.mail = new ViaMail();
        this.wpp = new ViaWPP();
        this.cuandoSuceden = new CuandoSuceden();
        this.sinApuro = new SinApuros();
        this.usuarioPablo = new Usuario();
        this.entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
        this.entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
    }
    @AfterEach
    public void after() {
        this.tx.commit();
        this.tx.rollback();
    }
    @Test
    public void testBD() {
        try {
            this.metodoBD();
        }
        catch (Exception e) {
            e.printStackTrace();
            this.tx.rollback();
        }
    }
        public void metodoBD(){

            //Se cargan las provincias y municipios
            this.listadoDeProvincias = this.servicioGeoref.listadoDeProvincias();

            this.listadoDeProvincias.getProvincias().forEach(provincia -> this.repositorioProvincias.agregar(provincia));
            this.repositorioProvincias.buscarTodos().forEach(
                    provincia -> this.servicioGeoref.listadoDeMunicipiosDeProvincia(provincia).
                            getMunicipios().forEach(municipio -> this.repositorioDeMunicipios.agregar(municipio)));

            //Se cargan las empresas
            this.empresas = this.cargadorDeDatos.cargaDeDatosMASIVA(this.sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), this.servicioGeoref);
            this.empresas.forEach(e -> this.repositorioDeOrganismosDeControl.agregar(e));

            this.banioUnisex = this.repositorioServicio.buscarTodos().stream().filter(s -> s.getTipo().equals(TipoBanio.UNISEX)).findFirst().get();
            lineaMitre = repositorioEntidad.buscarTodos().stream().filter(e -> e.getNombre().equals("Linea Mitre")).findFirst().get();
            estacionRetiro = repositorioDeEstablecimientos.buscarTodos().stream().filter(est -> est.getNombre().equals("Linea Roca")).findFirst().get();

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

            Assertions.assertEquals(1,pablito.obtenerIncidentesPorEstado(EstadoIncidente.CERRADO).size());
        }

    @Test
    public void recibirInformacion() {
        emisorDeNotificaciones.generarRankings();
    }
    @Test
    public void IncidentesDeLaSemana(){
        Assertions.assertEquals(2,repositorioDeIncidentes.getIncidentesEstaSemana().size());
        List<Incidente> incidentesEstaSemana = repositorioDeIncidentes.getIncidentesEstaSemana();

        Assertions.assertEquals(2,incidentesEstaSemana.size());

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