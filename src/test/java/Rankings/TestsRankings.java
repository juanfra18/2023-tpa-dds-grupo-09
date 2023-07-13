package Rankings;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.RepositorioDeEmpresas;
import domain.Incidentes.ReporteDeIncidente;
import domain.Incidentes.RepositorioDeIncidentes;
import domain.Personas.MiembroDeComunidad;
import domain.Personas.Rol;
import domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import domain.Rankings.EntidadesQueSolucionanMasLento;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.APIs.Georef.AdapterServicioGeo;
import services.Localizacion.Municipio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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




    @BeforeEach
    public void init() throws IOException {
        entidadesQueSolucionanMasLento = new EntidadesQueSolucionanMasLento();
        entidadesConMayorCantidadDeIncidentes = new EntidadesConMayorCantidadDeIncidentes();
        repositorioDeIncidentes = new RepositorioDeIncidentes();
        entidades = new ArrayList<Entidad>();
        //repositorioDeEmpresas = new RepositorioDeEmpresas(new CargadorDeDatos(),servicioGeo);
        //repositorioDeEmpresas.getEmpresas().forEach(empresa -> empresa.getEntidadesPrestadoras().forEach(ep -> entidades.addAll(ep.getEntidades())));


        pablo = new MiembroDeComunidad("perez", "pablo", "perez.pablito@gmail.com","123456789");
        maria = new MiembroDeComunidad("llaurado", "maria", "llauradom@gmail.com","987654321");
        julieta = new MiembroDeComunidad("alegre", "julieta", "alegre.juli@gmail.com","654658425");

        MockitoAnnotations.openMocks(this);
        servicioGeo = mock(AdapterServicioGeo.class);
        when(servicioGeo.obtenerMunicipio("General Alvarado")).thenReturn(generalAlvarado);
        when(servicioGeo.obtenerMunicipio("Pinamar")).thenReturn(pinamar);

        banioHombres = new Banio("CABALLEROS");
        banioMujeres = new Banio("DAMAS");

        pablo.agregarMunicipio(generalAlvarado);
        pablo.agregarServicioDeInteres(banioHombres, Rol.valueOf("AFECTADO"));
        pablo.agregarMunicipio(generalAlvarado);
        pablo.agregarServicioDeInteres(banioMujeres, Rol.valueOf("OBSERVADOR"));
        maria.agregarMunicipio(generalAlvarado);
        maria.agregarServicioDeInteres(banioMujeres, Rol.valueOf("AFECTADO"));
        julieta.agregarMunicipio(generalAlvarado);
        julieta.agregarServicioDeInteres(banioMujeres, Rol.valueOf("AFECTADO"));

        lineaMitre = new Entidad("Linea Mitre","FERROCARRIL");

        estacionRetiro = new Establecimiento("Retiro","ESTACION", generalAlvarado);
        estacionRetiro.agregarServicio(banioHombres);

        estacionPinamar = new Establecimiento("Pinamar", "ESTACION", pinamar);
        estacionPinamar.agregarServicio(banioHombres);

        lineaMitre.agregarEstablecimiento(estacionRetiro);
        lineaMitre.agregarEstablecimiento(estacionPinamar);

        lineaRoca = new Entidad("Linea Roca","FERROCARRIL");
        lineaRoca.agregarEstablecimiento(estacionTolosa);

        estacionTolosa = new Establecimiento("Tolosa","ESTACION", generalAlvarado);
        estacionTolosa.agregarServicio(banioMujeres);

        estacionPinamar = new Establecimiento("Pinamar", "ESTACION", pinamar);
        estacionPinamar.agregarServicio(banioHombres);

        entidades.add(lineaMitre);
        entidades.add(lineaRoca);

        //LocalDateTime.parse("2015-08-04T10:11:30")


        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,11,10,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,7,11,11,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("ABIERTO",LocalDateTime.of(2023,7,11,12,10,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
        pablo.informarFuncionamiento(incidenteBanioHombre,repositorioDeIncidentes);

        incidenteBanioHombre = new ReporteDeIncidente("CERRADO",LocalDateTime.of(2023,7,11,12,13,30),pablo,lineaMitre, estacionPinamar,banioHombres,"Se rompíó el dispenser de jabón del baño de hombres");
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

    }


    @Test
    public void rankingSolucionanMasLento() {
        List<ReporteDeIncidente> ll = repositorioDeIncidentes.getIncidentesEstaSemana();
        entidadesQueSolucionanMasLento.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());

    }

    @Test
    public void rankingMayorCantidadDeIncidentes() {
        entidadesConMayorCantidadDeIncidentes.armarRanking(entidades,repositorioDeIncidentes.getIncidentesEstaSemana());
    }


}
