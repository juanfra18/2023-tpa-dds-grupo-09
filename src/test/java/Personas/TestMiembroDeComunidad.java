package Personas;

import domain.Entidades.Entidad;
import domain.Entidades.EntidadDeEstablecimiento;
import domain.Entidades.Establecimiento;
import domain.Personas.Interes;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Elevacion;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import services.Localizacion.Localizacion;
import services.Localizacion.Localizador;

import java.io.IOException;
import java.util.List;

public class TestMiembroDeComunidad {

    private MiembroDeComunidad miembro;

    @BeforeEach
    public void setup() throws IOException {
        miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com");
        Localizacion almagro = Localizador.devolverLocalizacion(2035);
        miembro.agregarLocalizacion(2035);
    }

    @Test
    public void testInteres() throws IOException {
        Entidad santander = new EntidadDeEstablecimiento("Santander Rio Argentina", 2);
        miembro.agregarEntidadDeInteres(santander);

        Establecimiento sucursalAlmagro = new Establecimiento("Sucursal Almagro", "SUCURSAL", 2035);
        santander.agregarEstablecimiento(sucursalAlmagro);

        Servicio ascensor = new Elevacion("ASCENSOR");
        sucursalAlmagro.agregarServicio(ascensor);
        miembro.agregarServicioDeInteres(ascensor);

        List<Entidad> entidadesDeInteres = miembro.getEntidadesDeInteres();
        List<Localizacion> localizaciones = miembro.getLocalizaciones();
        List<Servicio> serviciosDeInteres = miembro.getServiciosDeInteres();
        miembro.interes(entidadesDeInteres, localizaciones, serviciosDeInteres);

        List<Interes> intereses = miembro.getIntereses();
        assertThat("Numero de intereses debería ser 1", intereses.size(), is(equalTo(1)));

        Interes interes = intereses.get(0);
        assertThat("ERROR EN entidad", interes.getEntidades().get(0), is(equalTo(santander)));
        assertThat("ERROR EN establecimiento", interes.getEstablecimientos().get(0), is(equalTo(sucursalAlmagro)));
        assertThat("ERROR EN servicio", interes.getServicios().get(0), is(equalTo(ascensor)));
        assertThat("ERROR EN localizacion", interes.getLocalizaciones().get(0), is(equalTo(2035)));
    }
}
/*
    public void testearAgregarLocalizacion(MiembroDeComunidad persona, Localizacion localizacion) {

        Assertions.assertEquals(localizacion,persona.getLocalizaciones().get(0).id);

        Assertions.assertEquals(localizacion.nombre,persona.getLocalizaciones().get(0).nombre);
        Assertions.assertSame(localizacion,persona.getLocalizaciones().get(0));
        Assertions.assertTrue(localizacion.equals(persona.getLocalizaciones().get(0)));
    }
    */
