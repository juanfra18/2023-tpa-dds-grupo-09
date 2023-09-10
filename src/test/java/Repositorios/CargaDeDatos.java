package Repositorios;

import domain.Entidades.Entidad;
import domain.Entidades.EntidadPrestadora;
import domain.Entidades.Establecimiento;
import domain.Incidentes.Incidente;
import domain.Incidentes.Posicion;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.EmisorDeNotificaciones;
import domain.Notificaciones.FormaDeNotificar;
import domain.Notificaciones.MedioDeComunicacion;
import domain.Persistencia.Repositorios.*;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import services.APIs.Georef.AdapterServicioGeo;
import services.Localizacion.Municipio;

import java.util.ArrayList;
import java.util.List;

public class CargaDeDatos {
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
    private Comunidad comunidad;
    private Comunidad comunidad2;
    private EmisorDeNotificaciones emisorDeNotificaciones;
    private EntidadPrestadora entidadPrestadora;
    private Posicion posicion1;
    private Posicion posicion2;
    private MedioDeComunicacion mail; //Ver si que hay que mockear
    private MedioDeComunicacion wpp;

    private FormaDeNotificar cuandoSuceden;
    private FormaDeNotificar sinApuro;

    @BeforeEach
    public void init() {
        repositorioDeIncidentes = new RepositorioDeIncidentes();
        entidades = new ArrayList<>();
        repositorioDeEmpresas = new RepositorioDeEmpresas();
        emisorDeNotificaciones = EmisorDeNotificaciones.getInstancia();
        comunidad = new Comunidad("Los+Capos", emisorDeNotificaciones);
        comunidad2 = new Comunidad("Los+Piolas", emisorDeNotificaciones);


        cuandoSuceden = new CuandoSuceden();
        sinApuro = new SinApuros();

        pablo = new MiembroDeComunidad("perez", "pablo", "juanpaoli@gmail.com","123456789", cuandoSuceden, mail,repositorioDeIncidentes);
        maria = new MiembroDeComunidad("llaurado", "maria", "llauradom@gmail.com","987654321", cuandoSuceden, mail,repositorioDeIncidentes);
        julieta = new MiembroDeComunidad("alegre", "julieta", "alegre.juli@gmail.com","654658425", sinApuro, wpp,repositorioDeIncidentes);


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



    }


    }