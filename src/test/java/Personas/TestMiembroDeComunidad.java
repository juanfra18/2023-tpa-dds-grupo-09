package Personas;

import domain.Entidades.Entidad;
import domain.Entidades.EntidadDeEstablecimiento;
import domain.Entidades.Establecimiento;
import domain.Personas.Interes;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Elevacion;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.Assertions;
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
    private Localizacion almagro;

    @BeforeEach
    public void setup() throws IOException {
        miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com");
        Localizacion almagro = Localizador.devolverLocalizacion(2035);
        miembro.agregarLocalizacion(almagro.getId());
    }

    @Test
    public void testInteres() throws IOException {
        Entidad santander = new EntidadDeEstablecimiento("Santander Rio Argentina", 2);
        Establecimiento sucursalAlmagro = new Establecimiento("Sucursal Almagro", "SUCURSAL", 2035);
        santander.agregarEstablecimiento(sucursalAlmagro);
        miembro.agregarEntidadDeInteres(santander);

        Servicio ascensor = new Elevacion("ASCENSOR");
        sucursalAlmagro.agregarServicio(ascensor);
        miembro.agregarServicioDeInteres(ascensor);

        miembro.agregarInteres();

        List<Interes> intereses = miembro.getIntereses();
        assertThat("Numero de intereses debería ser 1", intereses.size(), is(equalTo(1)));

        Interes interes = intereses.get(0);
        assertThat("ERROR EN entidad", interes.getEntidad(), is(equalTo(santander)));
        assertThat("ERROR EN establecimiento", interes.getEstablecimiento(), is(equalTo(sucursalAlmagro)));
        assertThat("ERROR EN servicio", interes.getServicio(), is(equalTo(ascensor)));
        assertThat("ERROR EN localizacion", interes.getLocalizacion().getId(), is(equalTo(2035)));
    }

    @Test
    public void testearAgregarLocalizacion() {
        Assertions.assertEquals(almagro.getId(), miembro.getLocalizaciones().get(0).getId());
    }

}