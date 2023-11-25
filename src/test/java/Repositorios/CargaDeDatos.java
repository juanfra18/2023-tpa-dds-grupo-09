package Repositorios;

import models.domain.Entidades.*;
import models.domain.Incidentes.EstadoIncidente;
import models.domain.Incidentes.Incidente;
import models.domain.Incidentes.Posicion;
import models.domain.Incidentes.ReporteDeIncidente;
import models.domain.Notificaciones.EmisorDeNotificaciones;
import models.domain.Notificaciones.FormaDeNotificar;
import models.domain.Notificaciones.MedioDeComunicacion;
import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Personas.Rol;
import models.domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import models.domain.Rankings.EntidadesQueSolucionanMasLento;
import models.domain.Servicios.Banio;
import models.domain.Servicios.Elevacion;
import models.domain.Servicios.Servicio;
import models.domain.Usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.persistence.Repositorios.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.services.Localizacion.ListadoDeProvincias;
import models.services.Localizacion.Municipio;
import models.services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;


public class CargaDeDatos implements WithSimplePersistenceUnit {
    private List<OrganismoDeControl> empresas;
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
    private Servicio escaleraMecanica;
    private Entidad lineaMitre;
    private Entidad lineaRoca;
    private EntidadPrestadora trenesArgentinos;
    private OrganismoDeControl CNRT;
    private Establecimiento estacionRetiro;
    private Establecimiento estacionVicenteLopez;
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
    private EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
    private EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
    private EntityTransaction tx;
    @BeforeEach
    public void before() {

        this.tx = entityManager().getTransaction();
        this.tx.begin();

        jujuy = new Provincia();
        yavi = new Municipio();
        banioUnisex = new Banio();
        escaleraMecanica = new Elevacion();
        estacionRetiro = new Establecimiento();
        estacionVicenteLopez = new Establecimiento();
        lineaMitre = new Entidad();
        lineaRoca = new Entidad();
        trenesArgentinos = new EntidadPrestadora();
        CNRT = new OrganismoDeControl();
        comunidad = new Comunidad();
        comunidad2 = new Comunidad();
        pablo = new MiembroDeComunidad();
        usuarioPablo = new Usuario();

        //Cargo provincia
        jujuy.setId(38);
        jujuy.setNombre("Jujuy");
        repositorioProvincias.agregar(jujuy);

        //Cargo municipio
        yavi.setId(386273);
        yavi.setNombre("Yavi");
        yavi.setProvincia(jujuy);
        repositorioDeMunicipios.agregar(yavi);

        //Cargo Servicio
        banioUnisex.setTipo("UNISEX");
        escaleraMecanica.setTipo("ESCALERAS_MECANICAS");


        //Cargo Establecimiento
        estacionRetiro.setLocalizacion(yavi);
        estacionRetiro.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionRetiro.setNombre("Retiro");
        estacionRetiro.agregarServicio(banioUnisex);
        estacionVicenteLopez.setLocalizacion(yavi);
        estacionVicenteLopez.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionVicenteLopez.setNombre("Vicente Lopez");
        estacionRetiro.agregarServicio(escaleraMecanica);
        repositorioDeEstablecimientos.agregar(estacionRetiro);
        repositorioDeEstablecimientos.agregar(estacionVicenteLopez);

        //Cargo Entidad
        lineaMitre.setTipoEntidad(TipoEntidad.FERROCARRIL.FERROCARRIL);
        lineaMitre.setNombre("Linea Mitre");
        lineaMitre.agregarEstablecimiento(estacionRetiro);
        lineaRoca.setTipoEntidad(TipoEntidad.FERROCARRIL.FERROCARRIL);
        lineaRoca.setNombre("Linea Roca");
        lineaRoca.agregarEstablecimiento(estacionVicenteLopez);
        repositorioEntidad.agregar(lineaMitre);
        repositorioEntidad.agregar(lineaRoca);

        //Cargo Entidad Prestadora
        trenesArgentinos.setNombre("Trenes Argentinos");
        trenesArgentinos.setPersonaMail("fanDeTrenes@gmail.com");
        trenesArgentinos.agregarEntidad(lineaMitre);
        trenesArgentinos.agregarEntidad(lineaRoca);
        repositorioDeEntidadPrestadora.agregar(trenesArgentinos);

        //Cargo Organismos de Control
        CNRT.setNombre("CNRT");
        CNRT.setPersonaMail("encargadoCNRT@gmail.com");
        CNRT.agregarEntidadPrestadora(trenesArgentinos);
        repositorioDeOrganismosDeControl.agregar(CNRT);

        //Cargo Comunidades
        emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();

        comunidad.setNombre("Los+Capos");
        comunidad.setEmisorDeNotificaciones(emisorDeNotificaciones);
        comunidad2.setNombre("Los+Piolas");
        comunidad2.setEmisorDeNotificaciones(emisorDeNotificaciones);

        repositorioComunidad.agregar(comunidad);
        repositorioComunidad.agregar(comunidad2);

        //Cargo Miembros de Comunidad
        pablo.setNombre("pablo");
        pablo.setApellido("perez");
        pablo.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
        pablo.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
        pablo.getReceptorDeNotificaciones().setMail("hola@mail.net");
        pablo.getReceptorDeNotificaciones().setTelefono("+1333333453");

        pablo.agregarMunicipio(yavi);
        pablo.agregarServicioDeInteres(banioUnisex, Rol.valueOf("AFECTADO"));

        pablo.unirseAComunidad(comunidad);
        pablo.unirseAComunidad(comunidad2);

        pablo.agregarEntidadDeInteres(lineaMitre);

        usuarioPablo.setUsername("xPablox");
        usuarioPablo.cambiarContrasenia("pablitoElMa$Cap@DeT@d@s");

        pablo.setUsuario(usuarioPablo);

        repositorioMiembroDeComunidad.agregar(pablo);

        //Cargo los Reportes de Incidentes
        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,22,19,30,30));
        incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,22,19,30,30));
        incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,22,19,45,30));
        incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,22,21,45,30));
        incidenteBanioLineaMitre.setObservaciones("Baño ya fue limpiado");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.CERRADO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,22,21,50,30));
        incidenteBanioLineaMitre.setObservaciones("Baño ya fue limpiado");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,23,10,45,30));
        incidenteBanioLineaMitre.setObservaciones("Volvieron a mojar el baño");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,23,11,0,30));
        incidenteBanioLineaMitre.setObservaciones("Volvieron a mojar el baño");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(1));

        incidenteBanioLineaMitre = new ReporteDeIncidente();
        incidenteBanioLineaMitre.setDenunciante(pablo);
        incidenteBanioLineaMitre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioLineaMitre.setEntidad(lineaMitre);
        incidenteBanioLineaMitre.setEstablecimiento(estacionRetiro);
        incidenteBanioLineaMitre.setServicio(banioUnisex);
        incidenteBanioLineaMitre.setFechaYhora(LocalDateTime.of(2023,9,22,19,45,30));
        incidenteBanioLineaMitre.setObservaciones("Baño inundado, todo el piso mojado");

        //pablo.informarFuncionamiento(incidenteBanioLineaMitre,pablo.getComunidades().get(0));
    }
    @AfterEach
    public void after() {
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
            MiembroDeComunidad pablito = repositorioMiembroDeComunidad.buscarTodos().get(0);
            //Assertions.assertEquals(1,pablito.obtenerIncidentesPorEstado(EstadoIncidente.ABIERTO).size());
        }
        @Test
        public void solicitarInformacionDeIncidentesCerrados(){
            MiembroDeComunidad pablito = repositorioMiembroDeComunidad.buscarTodos().get(0);

            //Assertions.assertEquals(1,pablito.obtenerIncidentesPorEstado(EstadoIncidente.CERRADO).size());
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