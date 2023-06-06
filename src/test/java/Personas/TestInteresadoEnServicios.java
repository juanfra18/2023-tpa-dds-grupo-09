package Personas;

import domain.Personas.MiembroDeComunidad;
import org.junit.jupiter.api.BeforeAll;
import services.Localizacion.Localizacion;

public class TestInteresadoEnServicios {

    static MiembroDeComunidad interesado1;

    public void testearAgregarLocalizacion(MiembroDeComunidad persona, Localizacion localizacion) {

        //Assertions.assertEquals(localizacion,persona.getLocalizaciones().get(0).id);

        //Assertions.assertEquals(localizacion.nombre,persona.getLocalizaciones().get(0).nombre);
        //Assertions.assertSame(localizacion,persona.getLocalizaciones().get(0));
        //Assertions.assertTrue(localizacion.equals(persona.getLocalizaciones().get(0)));
    }
    @BeforeAll
    public static void init1(){
        interesado1 = new MiembroDeComunidad("perez","jose","perezjose@gmail.com");
    }


}