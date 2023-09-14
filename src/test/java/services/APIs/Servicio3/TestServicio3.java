package services.APIs.Servicio3;
import domain.Entidades.*;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.Posicion;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.*;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Personas.Rol;
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
import services.Localizacion.Municipio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestServicio3 {
    private List<Entidad> entidades;
    private RepositorioDeIncidentes repositorioDeIncidentes;
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
    private RepositorioComunidad repositorioComunidad;
    private Comunidad comunidad;
    private Comunidad comunidad2;
    private EmisorDeNotificaciones emisorDeNotificaciones;
    private EntidadPrestadora entidadPrestadora;
    private Posicion posicion1;
    private Posicion posicion2;
    @Mock
    private MedioDeComunicacion mail;
    @Mock
    private MedioDeComunicacion wpp;

    private FormaDeNotificar cuandoSuceden;
    private FormaDeNotificar sinApuro;

    @Test
    public void init() {

        repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
        repositorioComunidad =RepositorioComunidad.getInstancia();
        entidades = new ArrayList<>();
        emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();
        comunidad = new Comunidad();
        comunidad.setNombre("Los+Capos");
        comunidad.setEmisorDeNotificaciones(emisorDeNotificaciones);
        comunidad2.setNombre("Los+Piolas");
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
        }).when(mail).recibirNotificacion(Mockito.anyString(), Mockito.anyString(), "H");

        wpp = mock(ViaWPP.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                System.out.println("Mensaje " + args[0]);
                System.out.println("Asunto: " + args[1]);
                return null;
            }
        }).when(wpp).recibirNotificacion(Mockito.anyString(), Mockito.anyString(), "h");
        RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
        cuandoSuceden = new CuandoSuceden();
        sinApuro = new SinApuros();
        RepositorioMiembroDeComunidad repo = RepositorioMiembroDeComunidad.getInstancia();

        FormaDeNotificar formaDeNotificar = new CuandoSuceden();
        MedioDeComunicacion medioDeComunicacion = new ViaMail();
        MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
        miembroDeComunidad.setNombre("pablo");
        miembroDeComunidad.setApellido("perez");
        miembroDeComunidad.setRepositorioDeReportesDeIncidentes(repositorioDeReportesDeIncidentes);
        miembroDeComunidad.getReceptorDeNotificaciones().cambiarFormaDeNotificar(formaDeNotificar);
        miembroDeComunidad.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(medioDeComunicacion);
        miembroDeComunidad.getReceptorDeNotificaciones().setMail("hola@mail.net");
        miembroDeComunidad.getReceptorDeNotificaciones().setTelefono("+1333333453");

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

        lineaMitre = new Entidad();
        lineaMitre.setNombre("Linea Mitre");
        lineaMitre.setTipoEntidad(TipoEntidad.FERROCARRIL);

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

        lineaMitre.agregarEstablecimiento(estacionRetiro);
        lineaMitre.agregarEstablecimiento(estacionPinamar);

        lineaRoca = new Entidad();
        lineaRoca.setNombre("Linea Roca");
        lineaRoca.setTipoEntidad(TipoEntidad.FERROCARRIL);

        estacionTolosa = new Establecimiento();
        estacionTolosa.setNombre("Tolosa");
        estacionTolosa.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionTolosa.setLocalizacion(servicioGeo.obtenerMunicipio("General Alvarado"));
        estacionTolosa.agregarServicio(banioMujeres);

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


        incidenteBanioHombre = new ReporteDeIncidente();
        incidenteBanioHombre.setClasificacion(EstadoIncidente.ABIERTO);
        incidenteBanioHombre.setFechaYhora(LocalDateTime.of(2023, 8, 22, 10, 10, 30));
        incidenteBanioHombre.setDenunciante(pablo);
        incidenteBanioHombre.setEstablecimiento(estacionPinamar);
        incidenteBanioHombre.setServicio(banioHombres);
        incidenteBanioHombre.setObservaciones("Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(0));
        /*
        incidenteBanioHombre = new ReporteDeIncidente("CERRADO", LocalDateTime.of(2023, 8, 22, 17, 10, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(0));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 22, 19, 10, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO", LocalDateTime.of(2023, 8, 23, 20, 10, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 24, 10, 10, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 25, 12, 10, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO", LocalDateTime.of(2023, 8, 25, 17, 10, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 26, 10, 14, 30), pablo, lineaMitre, estacionPinamar, banioHombres, "Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre, pablo.getComunidades().get(0));

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 23, 10, 15, 30), maria, lineaRoca, estacionTolosa, banioMujeres, "Se robaron el inodoro");
        maria.informarFuncionamiento(incidenteBanioMujer, maria.getComunidades().get(0));

        incidenteBanioMujer = new ReporteDeIncidente("CERRADO", LocalDateTime.of(2023, 8, 23, 19, 20, 30), julieta, lineaRoca, estacionTolosa, banioMujeres, "Devolvieron el inodoro");
        julieta.informarFuncionamiento(incidenteBanioMujer, julieta.getComunidades().get(0));

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 25, 10, 25, 30), pablo, lineaRoca, estacionTolosa, banioMujeres, "Baño inundado");
        pablo.informarFuncionamiento(incidenteBanioMujer, pablo.getComunidades().get(1));

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO", LocalDateTime.of(2023, 8, 26, 19, 30, 30), pablo, lineaRoca, estacionTolosa, banioMujeres, "Baño inundado, todo el piso mojado");
        pablo.informarFuncionamiento(incidenteBanioMujer, pablo.getComunidades().get(0));

*/
    }


    @Test
    public void ranking() {
        RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();
        RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();
        RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();

        Servicio3JSON servicio3JSON= Servicio3JSON.instancia();
        List<Entidad> respuesta = servicio3JSON.obtenerRanking(repositorioEntidad.buscarTodos(), repositorioDeIncidentes.getIncidentesEstaSemana(),repositorioComunidad.buscarTodos());
        respuesta.forEach(r -> System.out.println(r.getNombre()));
    }

}