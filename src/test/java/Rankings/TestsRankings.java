package Rankings;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.EntidadPrestadora;
import domain.Entidades.Establecimiento;
import domain.Entidades.RepositorioDeEmpresas;
import domain.Incidentes.Posicion;
import domain.Incidentes.ReporteDeIncidente;
import domain.Incidentes.RepositorioDeIncidentes;
import domain.Notificaciones.AdapterViaMail;
import domain.Notificaciones.EmisorDeNotificaciones;
import domain.Notificaciones.ViaMailJavax;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Personas.Rol;
import domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import domain.Rankings.EntidadesQueSolucionanMasLento;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.APIs.Georef.AdapterServicioGeo;
import services.Localizacion.Municipio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestsRankings {
    private EntidadesQueSolucionanMasLento entidadesQueSolucionanMasLento;
    private EntidadesConMayorCantidadDeIncidentes entidadesConMayorCantidadDeIncidentes;
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
    private Comunidad comunidad;
    private Comunidad comunidad2;
    private EmisorDeNotificaciones emisorDeNotificaciones;
    private EntidadPrestadora entidadPrestadora;
    private Posicion posicion1;
    private Posicion posicion2;




    @BeforeEach
    public void init() throws IOException {
        entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
        entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
        repositorioDeIncidentes = new RepositorioDeIncidentes();
        entidades = new ArrayList<Entidad>();
        //repositorioDeEmpresas = new RepositorioDeEmpresas(new CargadorDeDatos(),servicioGeo);
        //repositorioDeEmpresas.getEmpresas().forEach(empresa -> empresa.getEntidadesPrestadoras().forEach(ep -> entidades.addAll(ep.getEntidades())));
        emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();
        comunidad = new Comunidad("Los+Capos", emisorDeNotificaciones);
        comunidad2 = new Comunidad("Los+Piolas", emisorDeNotificaciones);

        pablo = new MiembroDeComunidad("perez", "pablo", "juanpaoli@gmail.com","123456789");
        maria = new MiembroDeComunidad("llaurado", "maria", "llauradom@gmail.com","987654321");
        julieta = new MiembroDeComunidad("alegre", "julieta", "alegre.juli@gmail.com","654658425");

        MockitoAnnotations.openMocks(this);
        generalAlvarado = mock(Municipio.class);
        pinamar = mock(Municipio.class);
        when(generalAlvarado.getId()).thenReturn(1);
        when(pinamar.getId()).thenReturn(2);
        servicioGeo = mock(AdapterServicioGeo.class);
        when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
        when(servicioGeo.obtenerMunicipio("Pinamar")).thenReturn(pinamar);


        banioHombres = new Banio("CABALLEROS");
        banioMujeres = new Banio("DAMAS");

        pablo.agregarMunicipio(servicioGeo.obtenerMunicipio("General Alvarado"));
        pablo.agregarMunicipio(servicioGeo.obtenerMunicipio("Pinamar"));
        pablo.agregarServicioDeInteres(banioHombres, Rol.valueOf("AFECTADO"));
        pablo.agregarServicioDeInteres(banioMujeres, Rol.valueOf("OBSERVADOR"));
        maria.agregarMunicipio(servicioGeo.obtenerMunicipio("General Alvarado"));
        maria.agregarServicioDeInteres(banioMujeres, Rol.valueOf("AFECTADO"));
        julieta.agregarMunicipio(servicioGeo.obtenerMunicipio("General Alvarado"));
        julieta.agregarServicioDeInteres(banioMujeres, Rol.valueOf("AFECTADO"));

        lineaMitre = new Entidad("Linea Mitre","FERROCARRIL");

        estacionRetiro = new Establecimiento("Retiro","ESTACION", servicioGeo.obtenerMunicipio("General Alvarado"));
        estacionRetiro.agregarServicio(banioHombres);

        estacionPinamar = new Establecimiento("Pinamar", "ESTACION", servicioGeo.obtenerMunicipio("Pinamar"));
        estacionPinamar.agregarServicio(banioHombres);

        lineaMitre.agregarEstablecimiento(estacionRetiro);
        lineaMitre.agregarEstablecimiento(estacionPinamar);

        lineaRoca = new Entidad("Linea Roca","FERROCARRIL");
        lineaRoca.agregarEstablecimiento(estacionTolosa);

        estacionTolosa = new Establecimiento("Tolosa","ESTACION", servicioGeo.obtenerMunicipio("General Alvarado"));
        estacionTolosa.agregarServicio(banioMujeres);

        estacionPinamar = new Establecimiento("Pinamar", "ESTACION", servicioGeo.obtenerMunicipio("Pinamar"));
        estacionPinamar.agregarServicio(banioHombres);

        entidades.add(lineaMitre);
        entidades.add(lineaRoca);

        //LocalDateTime.parse("2015-08-04T10:11:30")
        pablo.unirseAComunidad(comunidad);
        pablo.unirseAComunidad(comunidad2);

        julieta.unirseAComunidad(comunidad);
        maria.unirseAComunidad(comunidad);

        pablo.getReceptorDeNotificaciones().cambiarMedioDeComunicacion("Mail"); //descomentar estas 3 líneas para mandar mail/whatsapp (no hace nada en wsp)
        pablo.getReceptorDeNotificaciones().cambiarFormaDeNotificar("CUANDO_SUCEDEN");
        pablo.agregarEntidadDeInteres(lineaMitre);

        julieta.getReceptorDeNotificaciones().cambiarMedioDeComunicacion("WhatsApp");
        maria.getReceptorDeNotificaciones().cambiarMedioDeComunicacion("WhatsApp");
        julieta.getReceptorDeNotificaciones().cambiarFormaDeNotificar("CUANDO_SUCEDEN");
        maria.getReceptorDeNotificaciones().cambiarFormaDeNotificar("SIN_APUROS");



        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,3,10,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        /*
        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,11,10,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,7,11,11,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se arregló el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,11,12,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,7,11,12,13,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se arregló el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,11,10,14,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,11,10,15,30),maria,lineaRoca, estacionTolosa,banioMujeres,"Se robaron el inodoro");
        maria.informarFuncionamiento(incidenteBanioMujer,repositorioDeIncidentes);

        incidenteBanioMujer = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,7,11,10,20,30),julieta,lineaRoca, estacionTolosa,banioMujeres,"Devolvieron el inodoro");
        julieta.informarFuncionamiento(incidenteBanioMujer,repositorioDeIncidentes);

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,12,10,25,30),pablo,lineaRoca, estacionTolosa,banioMujeres,"Baño inundado");
        pablo.informarFuncionamiento(incidenteBanioMujer,repositorioDeIncidentes);

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,12,10,30,30),pablo,lineaRoca, estacionTolosa,banioMujeres,"Baño inundado, todo el piso mojado");
        pablo.informarFuncionamiento(incidenteBanioMujer,repositorioDeIncidentes);
        */
    }


    @Test
    public void rankingSolucionanMasLento() {
        List<ReporteDeIncidente> ll = repositorioDeIncidentes.getIncidentesEstaSemana();
        entidadesQueSolucionanMasLento.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());
    }

    @Test
    public void solicitarInformacionDeIncidentesAbiertos(){
        for (ReporteDeIncidente incidente : pablo.solicitarInformacionDeIncidentesAbiertos()){
            System.out.println(incidente.mensaje());
        }
    }
    @Test
    public void solicitarInformacionDeIncidentesCerrados(){
        for (ReporteDeIncidente incidente : pablo.solicitarInformacionDeIncidentesCerrados()){
            System.out.println(incidente.mensaje());
        }
    }
    @Test
    public void rankingMayorCantidadDeIncidentes() {
        entidadesConMayorCantidadDeIncidentes.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());
    }
    @Test
    public void recibirInformacion() {
        entidadesConMayorCantidadDeIncidentes.armarRanking(entidades, repositorioDeIncidentes.getIncidentesEstaSemana()); //ranking 2
        entidadesQueSolucionanMasLento.armarRanking(entidades, repositorioDeIncidentes.getIncidentesEstaSemana());
        AdapterViaMail viaMail = new ViaMailJavax();
        entidadPrestadora = new EntidadPrestadora("La Prestadora");
        entidadPrestadora.agregarEntidad(lineaMitre);
        entidadPrestadora.agregarEntidad(lineaRoca);
        entidadPrestadora.asignarPersona("juanpaoli@gmail.com");
        entidadPrestadora.enviarInformacion(Config.RANKING_1, viaMail);
    }

    @Test
    public void ReportarUnIncidente(){
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        Assertions.assertEquals(incidenteBanioHombre.getObservaciones(),pablo.getComunidades().get(0).getIncidentesDeLaComunidad().get(0).getObservaciones());
        Assertions.assertEquals(incidenteBanioHombre.getServicio(),pablo.getComunidades().get(0).getIncidentesDeLaComunidad().get(0).getServicio());
        Assertions.assertEquals(incidenteBanioHombre.getEstablecimiento(),pablo.getComunidades().get(0).getIncidentesDeLaComunidad().get(0).getEstablecimiento());

        Assertions.assertEquals(incidenteBanioHombre.getObservaciones(),pablo.getComunidades().get(1).getIncidentesDeLaComunidad().get(0).getObservaciones());
        Assertions.assertEquals(incidenteBanioHombre.getServicio(),pablo.getComunidades().get(1).getIncidentesDeLaComunidad().get(0).getServicio());
        Assertions.assertEquals(incidenteBanioHombre.getEstablecimiento(),pablo.getComunidades().get(1).getIncidentesDeLaComunidad().get(0).getEstablecimiento());
    }

    @Test
    public void IncidentesDeLaSemana(){
        Assertions.assertEquals(9,repositorioDeIncidentes.getIncidentesEstaSemana().size());
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
}