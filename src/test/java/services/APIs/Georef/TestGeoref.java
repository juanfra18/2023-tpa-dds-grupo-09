package services.APIs.Georef;

import org.junit.jupiter.api.BeforeAll;
import services.Localizacion.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

public class TestGeoref {
    static ServicioGeoref servicioGeoref;
    @BeforeAll
    public static void init(){
        servicioGeoref = ServicioGeoref.instancia();
    }
    @Test
    public void testProvincias() throws IOException {
        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();

        for(Provincia unaProvincia: listadoDeProvinciasArgentinas.provincias){
            System.out.println(unaProvincia.nombre +" " + unaProvincia.id);
        }

        Optional<Provincia> posibleProvincia = listadoDeProvinciasArgentinas.provinciaDeId(6);

        Assertions.assertEquals((posibleProvincia.get().nombre), "Buenos Aires");
    }
    @Test
    public void testMunicipios() throws IOException {
        ListadoDeMunicipios municipiosDeLaProvincia = servicioGeoref.listadoDeMunicipiosDeProvincia(servicioGeoref.listadoDeProvincias().provinciaDeId(6).get());
        for(Municipio unMunicipio: municipiosDeLaProvincia.municipios){
            System.out.println(unMunicipio.nombre + " " + unMunicipio.id);
        }
    }
}
