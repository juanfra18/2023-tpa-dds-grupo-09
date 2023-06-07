package Personas;

import domain.Entidades.Entidad;
import domain.Entidades.EntidadDeEstablecimiento;
import domain.Entidades.Establecimiento;
import domain.Personas.Comunidad;
import domain.Personas.Interes;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Elevacion;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import services.Localizacion.Localizacion;
import services.Localizacion.Localizador;

import java.io.IOException;
import java.util.List;

public class TestInteresesMiembro {

    private MiembroDeComunidad miembro;
    static Localizacion almagro;

    @BeforeEach
    private void setupInteres() throws IOException {
        miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com");
        almagro = Localizador.devolverLocalizacion(2035);
        miembro.agregarLocalizacion(almagro.getId());

        Entidad santander = new EntidadDeEstablecimiento("Santander Rio Argentina", 2);
        Establecimiento sucursalAlmagro = new Establecimiento("Sucursal Almagro", "SUCURSAL", 2035);
        santander.agregarEstablecimiento(sucursalAlmagro);
        miembro.agregarEntidadDeInteres(santander);

        Servicio ascensor = new Elevacion("ASCENSOR");
        sucursalAlmagro.agregarServicio(ascensor);
        miembro.agregarServicioDeInteres(ascensor);

        miembro.agregarInteres();
    }

    @Test
    public void testInteres() throws IOException {
        setupInteres();

        List<Interes> intereses = miembro.getIntereses();
        assertThat("Numero de intereses debería ser 1", intereses.size(), is(equalTo(1)));

        Interes interes = intereses.get(0);
        assertThat("ERROR EN entidad", interes.getEntidad(), is(equalTo(miembro.getEntidadesDeInteres().get(0))));
        assertThat("ERROR EN establecimiento", interes.getEstablecimiento(), is(equalTo(miembro.getEntidadesDeInteres().get(0).getEstablecimientos().get(0))));
        assertThat("ERROR EN servicio", interes.getServicio(), is(equalTo(miembro.getServiciosDeInteres().get(0))));
    }

    @Test
    public void testearUnirseAcomunidad() throws IOException {
        setupInteres();
        Interes interes = miembro.getIntereses().get(0);

        Comunidad comunidad = new Comunidad("Asociacion de Amor", interes);
        miembro.unirseAComunidad(comunidad);
        List<MiembroDeComunidad> miembros = comunidad.getMiembros();
        assertTrue(miembros.contains(miembro));
    }
}


