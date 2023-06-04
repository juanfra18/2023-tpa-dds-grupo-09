package Personas;

import domain.Personas.InteresadoEnServicios;
import domain.Usuario.RepositorioDeUsuarios;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Localizacion.Departamento;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import java.util.List;

public class TestInteresadoEnServicios {

    static InteresadoEnServicios interesado1;
    @BeforeAll
    public static void init1(){
        interesado1 = new InteresadoEnServicios("perez","jose","perezjose@gmail.com");
    }

    @Test
    public void testAgregarProvincia(){
        Provincia santiagoDelEstero = new Provincia(86);

        interesado1.agregarLocalizacion(86,0,0);

        Assertions.assertEquals(santiagoDelEstero.id,interesado1.getLocalizaciones().get(0).id);

        Assertions.assertEquals(santiagoDelEstero.nombre,interesado1.getLocalizaciones().get(0).nombre);
        //Assertions.assertSame(santiagoDelEstero,interesado1.getLocalizaciones().get(0));
        }
    @Test
    public void testAgregarDepartamento(){
        Departamento belen = new Departamento(10, 10035);

        interesado1.agregarLocalizacion(10,10035,0);

        Assertions.assertEquals(belen.id,interesado1.getLocalizaciones().get(0).id);

        Assertions.assertEquals(belen.nombre,interesado1.getLocalizaciones().get(0).nombre);
        //Assertions.assertSame(santiagoDelEstero,interesado1.getLocalizaciones().get(0));
    }

    @Test
    public void testAgregarMunicipio(){
        Municipio hualfin = new Municipio(10, 10035,100077);

        interesado1.agregarLocalizacion(10,10035,100077);

        Assertions.assertEquals(hualfin.id,interesado1.getLocalizaciones().get(0).id);

        Assertions.assertEquals(hualfin.nombre,interesado1.getLocalizaciones().get(0).nombre);
        //Assertions.assertSame(santiagoDelEstero,interesado1.getLocalizaciones().get(0));
    }

}