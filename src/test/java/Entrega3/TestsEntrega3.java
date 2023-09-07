package Entrega3;

import Config.Config;
import domain.Entidades.Entidad;
import domain.Entidades.EntidadPrestadora;
import domain.Entidades.Establecimiento;
import domain.Entidades.RepositorioDeEmpresas;
import domain.Incidentes.*;
import domain.Notificaciones.*;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import services.APIs.Georef.AdapterServicioGeo;
import services.Archivos.CargadorDeDatos;
import services.Localizacion.Municipio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestsEntrega3 {
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
    @Mock
    private MedioDeComunicacion mail;
    @Mock
    private MedioDeComunicacion wpp;

    private FormaDeNotificar cuandoSuceden;
    private FormaDeNotificar sinApuro;





    @BeforeEach
    public void init() {
        entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
        entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
        repositorioDeIncidentes = new RepositorioDeIncidentes();
        entidades = new ArrayList<>();
        //repositorioDeEmpresas = new RepositorioDeEmpresas(new CargadorDeDatos(),servicioGeo);
        //repositorioDeEmpresas.getEmpresas().forEach(empresa -> empresa.getEntidadesPrestadoras().forEach(ep -> entidades.addAll(ep.getEntidades())));
        emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();
        comunidad = new Comunidad("Los+Capos", emisorDeNotificaciones);
        comunidad2 = new Comunidad("Los+Piolas", emisorDeNotificaciones);

        //Se mockea el envio de Mails y Wpp
        MockitoAnnotations.openMocks(this);
        mail = mock(ViaMail.class);
        /*
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                System.out.println("Mensaje " + args[0]);
                System.out.println("Asunto: " + args[1]);
                return null;
            }
        }).when(mail).recibirNotificacion(Mockito.anyString(),Mockito.anyString());

        wpp = mock(ViaWPP.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                System.out.println("Mensaje " + args[0]);
                System.out.println("Asunto: " + args[1]);
                return null;
            }
        }).when(wpp).recibirNotificacion(Mockito.anyString(),Mockito.anyString());
        */
        cuandoSuceden = new CuandoSuceden();
        sinApuro = new SinApuros();

        pablo = new MiembroDeComunidad("perez", "pablo", "juanpaoli@gmail.com","123456789", cuandoSuceden, mail,repositorioDeIncidentes);
        maria = new MiembroDeComunidad("llaurado", "maria", "llauradom@gmail.com","987654321", cuandoSuceden, mail,repositorioDeIncidentes);
        julieta = new MiembroDeComunidad("alegre", "julieta", "alegre.juli@gmail.com","654658425", sinApuro, wpp,repositorioDeIncidentes);

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

        estacionTolosa = new Establecimiento("Tolosa","ESTACION", servicioGeo.obtenerMunicipio("General Alvarado"));
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



        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,22,10,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(0));
/*
        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,8,22,17,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(0));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,22,19,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,8,23,20,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,24,10,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,25,12,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,8,25,17,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(1));

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,26,10,14,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,pablo.getComunidades().get(0));

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,23,10,15,30),maria,lineaRoca, estacionTolosa,banioMujeres,"Se robaron el inodoro");
        maria.informarFuncionamiento(incidenteBanioMujer,maria.getComunidades().get(0));

        incidenteBanioMujer = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,8,23,19,20,30),julieta,lineaRoca, estacionTolosa,banioMujeres,"Devolvieron el inodoro");
        julieta.informarFuncionamiento(incidenteBanioMujer,julieta.getComunidades().get(0));

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,25,10,25,30),pablo,lineaRoca, estacionTolosa,banioMujeres,"Baño inundado");
        pablo.informarFuncionamiento(incidenteBanioMujer,pablo.getComunidades().get(1));

        incidenteBanioMujer = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,8,26,19,30,30),pablo,lineaRoca, estacionTolosa,banioMujeres,"Baño inundado, todo el piso mojado");
        pablo.informarFuncionamiento(incidenteBanioMujer,pablo.getComunidades().get(0));
*/


    }


    @Test
    public void rankingSolucionanMasLento() {
        entidadesQueSolucionanMasLento.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());
    }

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
    /*
    @Test
    public void recibirInformacion() {
        repositorioDeEmpresas = new RepositorioDeEmpresas(new CargadorDeDatos(), servicioGeo);
        emisorDeNotificaciones.generarRankings(repositorioDeEmpresas, repositorioDeIncidentes);
    }
    */

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
}