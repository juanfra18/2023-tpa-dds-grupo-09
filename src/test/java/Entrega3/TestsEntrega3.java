package Entrega3;

import Config.Config;
import domain.Entidades.*;
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
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import persistence.Repositorios.*;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import services.Localizacion.Municipio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestsEntrega3 {
    private List<OrganismoDeControl> empresas;
    private  CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    private SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
    private ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
    private EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento;
    private EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes;
    private List<Entidad> entidades;
    @Mock
    private AdapterServicioGeo servicioGeo;
    private Municipio generalAlvarado;
    private Municipio pinamar;
    private Servicio banioHombres;
    private Servicio banioMujeres;
    private Establecimiento estacionRetiro;
    private Establecimiento estacionTolosa;
    private Establecimiento estacionPinamar;
    private Entidad lineaMitre;
    private Entidad lineaRoca;
    private ReporteDeIncidente incidenteBanioHombre;
    private ReporteDeIncidente incidenteBanioMujer;
    private MiembroDeComunidad pablo;
    private MiembroDeComunidad maria;
    private MiembroDeComunidad julieta;
    private RepositorioDeEmpresas repositorioDeEmpresas;
    private Comunidad comunidad;
    private Comunidad comunidad2;
    private EmisorDeNotificaciones emisorDeNotificaciones;
    private EntidadPrestadora entidadPrestadora;
    private Posicion posicion1;
    private Posicion posicion2;
    private ReporteDeIncidente incidenteBanioLineaMitre;
    @Mock
    private MedioDeComunicacion mail;
    @Mock
    private MedioDeComunicacion wpp;

    private FormaDeNotificar cuandoSuceden;
    private FormaDeNotificar sinApuro;
    private RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
    private RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
    private RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
    private Incidente incidenteMockeado;
    @BeforeEach
    public void init() {
      MockitoAnnotations.openMocks(this);
      repositorioComunidad = mock(RepositorioComunidad.class);
      doNothing().when(repositorioComunidad).agregar(comunidad);
      doNothing().when(repositorioComunidad).agregar(comunidad2);

      MockitoAnnotations.openMocks(this);
      incidenteMockeado = mock(Incidente.class);
      repositorioDeIncidentes = mock(RepositorioDeIncidentes.class);
      doNothing().when(repositorioDeIncidentes).agregar(incidenteMockeado);

      MockitoAnnotations.openMocks(this);
      ReporteDeIncidente reporteDeIncidente = mock(ReporteDeIncidente.class);
      repositorioDeReportesDeIncidentes = mock(RepositorioDeReportesDeIncidentes.class);
      doNothing().when(repositorioDeReportesDeIncidentes).agregar(reporteDeIncidente);

      entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
        entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
        repositorioDeIncidentes = new RepositorioDeIncidentes();
        entidades = new ArrayList<>();

        comunidad = new Comunidad();
        comunidad.setNombre("Los+Capos");
        comunidad.setEmisorDeNotificaciones(emisorDeNotificaciones);
        comunidad2 = new Comunidad();
        comunidad2.setNombre("Los+Piola");
        comunidad2.setEmisorDeNotificaciones(emisorDeNotificaciones);

        //Se mockea el envio de Mails y Wpp
        MockitoAnnotations.openMocks(this);
        mail = mock(ViaMail.class);

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                System.out.println("Mensaje " + args[0]);
                System.out.println("Asunto: " + args[1]);
                return null;
            }
        }).when(mail).recibirNotificacion(Mockito.anyString(),Mockito.anyString(),Mockito.anyString());

        wpp = mock(ViaWPP.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                System.out.println("Mensaje " + args[0]);
                System.out.println("Asunto: " + args[1]);
                return null;
            }
        }).when(wpp).recibirNotificacion(Mockito.anyString(),Mockito.anyString(),Mockito.anyString());

        cuandoSuceden = new CuandoSuceden();
        sinApuro = new SinApuros();

        pablo = new MiembroDeComunidad();
        pablo.setNombre("pablo");
        pablo.setApellido("perez");
        pablo.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
        pablo.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
        pablo.getReceptorDeNotificaciones().setMail("pablop@mail.net");
        pablo.getReceptorDeNotificaciones().setTelefono("123456789");


        maria = new MiembroDeComunidad();
        maria.setNombre("maria");
        maria.setApellido("llaurado");
        maria.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
        maria.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
        maria.getReceptorDeNotificaciones().setMail("llauradom@mail.net");
        maria.getReceptorDeNotificaciones().setTelefono("987654321");

        julieta = new MiembroDeComunidad();
        julieta.setNombre("julieta");
        julieta.setApellido("alegre");
        julieta.getReceptorDeNotificaciones().cambiarFormaDeNotificar(sinApuro);
        julieta.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(wpp);
        julieta.getReceptorDeNotificaciones().setMail("alegre.juli@mail.net");
        julieta.getReceptorDeNotificaciones().setTelefono("123459876");

        MockitoAnnotations.openMocks(this);

        generalAlvarado = mock(Municipio.class);
        pinamar = mock(Municipio.class);
        when(generalAlvarado.getId()).thenReturn(1);
        when(pinamar.getId()).thenReturn(2);
        servicioGeo = mock(AdapterServicioGeo.class);
        when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
        when(servicioGeo.obtenerMunicipio("Pinamar")).thenReturn(pinamar);

        banioHombres = new Banio();
        banioHombres.setTipo("CABALLEROS");
        banioMujeres = new Banio();
        banioMujeres.setTipo("DAMAS");

        pablo.agregarMunicipio(servicioGeo.obtenerMunicipio("General Alvarado"));
        pablo.agregarMunicipio(servicioGeo.obtenerMunicipio("Pinamar"));
        pablo.agregarServicioDeInteres(banioHombres, Rol.valueOf("AFECTADO"));
        pablo.agregarServicioDeInteres(banioMujeres, Rol.valueOf("OBSERVADOR"));

        maria.agregarMunicipio(servicioGeo.obtenerMunicipio("General Alvarado"));
        maria.agregarServicioDeInteres(banioMujeres, Rol.valueOf("AFECTADO"));

        julieta.agregarMunicipio(servicioGeo.obtenerMunicipio("General Alvarado"));
        julieta.agregarServicioDeInteres(banioMujeres, Rol.valueOf("AFECTADO"));

        estacionRetiro = new Establecimiento();
        estacionRetiro.setNombre("Retiro");
        estacionRetiro.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionRetiro.setLocalizacion(servicioGeo.obtenerMunicipio("General Alvarado"));
        estacionRetiro.agregarServicio(banioHombres);

        estacionPinamar = new Establecimiento();
        estacionPinamar.setNombre("Pinamar");
        estacionPinamar.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionPinamar.setLocalizacion(servicioGeo.obtenerMunicipio("Pinamar"));
        estacionPinamar.agregarServicio(banioHombres);


        lineaMitre = new Entidad();
        lineaMitre.setNombre("Linea Mitre");
        lineaMitre.setTipoEntidad(TipoEntidad.FERROCARRIL);
        lineaMitre.agregarEstablecimiento(estacionRetiro);
        lineaMitre.agregarEstablecimiento(estacionPinamar);


        estacionTolosa = new Establecimiento();
        estacionTolosa.setNombre("Tolosa");
        estacionTolosa.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionTolosa.setLocalizacion(servicioGeo.obtenerMunicipio("General Alvarado"));
        estacionTolosa.agregarServicio(banioMujeres);


        lineaRoca = new Entidad();
        lineaRoca.setNombre("Linea Roca");
        lineaRoca.setTipoEntidad(TipoEntidad.FERROCARRIL);
        lineaRoca.agregarEstablecimiento(estacionTolosa);

        entidades.add(lineaMitre);
        entidades.add(lineaRoca);

        pablo.unirseAComunidad(comunidad);
        pablo.unirseAComunidad(comunidad2);

        julieta.unirseAComunidad(comunidad);
        maria.unirseAComunidad(comunidad);

        //pablo.getReceptorDeNotificaciones().cambiarMedioDeComunicacion("Mail"); //descomentar estas 3 líneas para mandar mail/whatsapp (no hace nada en wsp)
        //pablo.getReceptorDeNotificaciones().cambiarFormaDeNotificar("CUANDO_SUCEDEN");
        pablo.agregarEntidadDeInteres(lineaMitre);

        //julieta.getReceptorDeNotificaciones().cambiarMedioDeComunicacion("WhatsApp");
        //maria.getReceptorDeNotificaciones().cambiarMedioDeComunicacion("WhatsApp");
        //julieta.getReceptorDeNotificaciones().cambiarFormaDeNotificar("CUANDO_SUCEDEN");
        //maria.getReceptorDeNotificaciones().cambiarFormaDeNotificar("SIN_APUROS");


        //reportes de incidentes
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


    }


    @Test
    public void rankingSolucionanMasLento() {
        entidadesQueSolucionanMasLento.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());
    }

/*
    @Test
    public void solicitarInformacionDeIncidentesAbiertos(){
        List<Incidente> incidentesAbiertos = pablo.solicitarInformacionDeIncidentesAbiertos();
        if(!incidentesAbiertos.isEmpty())
        {
            for (Incidente incidente : incidentesAbiertos){
                System.out.println(incidente.mensaje());
            }
        }

    }
    @Test
    public void solicitarInformacionDeIncidentesCerrados(){
        List<Incidente> incidentesCerrados = pablo.solicitarInformacionDeIncidentesCerrados();
        if(!incidentesCerrados.isEmpty())
        {
            for (Incidente incidente : incidentesCerrados){
                System.out.println(incidente.mensaje());
            }
        }
    }

    @Test
    public void rankingMayorCantidadDeIncidentes() {
        entidadesConMayorCantidadDeIncidentes.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());
    }

    @Test
    public void recibirInformacion() {
        repositorioDeEmpresas = new RepositorioDeEmpresas(new CargadorDeDatos(), servicioGeo);
        emisorDeNotificaciones.generarRankings(repositorioDeEmpresas, repositorioDeIncidentes);
    }


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
    public void IncidentesDeLaSemana(){
        Assertions.assertEquals(2,repositorioDeIncidentes.getIncidentesEstaSemana().size());
        List<Incidente> i = repositorioDeIncidentes.getIncidentesEstaSemana();
        for(Incidente incidente : i)
        {

            System.out.println(incidente.mensaje());
        }

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