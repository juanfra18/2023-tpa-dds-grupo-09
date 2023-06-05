package Personas;

import domain.Personas.InteresadoEnServicios;
import domain.Seguridad.RegistroDeUsuarioException;
import domain.Usuario.RepositorioDeUsuarios;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Localizacion.Departamento;
import services.Localizacion.Localizacion;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import java.util.List;

public class TestInteresadoEnServicios {

    static InteresadoEnServicios interesado1;

    public void testearAgregarLocalizacion(InteresadoEnServicios persona, Localizacion localizacion) {

        //Assertions.assertEquals(localizacion,persona.getLocalizaciones().get(0).id);

        //Assertions.assertEquals(localizacion.nombre,persona.getLocalizaciones().get(0).nombre);
        //Assertions.assertSame(localizacion,persona.getLocalizaciones().get(0));
        //Assertions.assertTrue(localizacion.equals(persona.getLocalizaciones().get(0)));
    }
    @BeforeAll
    public static void init1(){
        interesado1 = new InteresadoEnServicios("perez","jose","perezjose@gmail.com");
    }


}