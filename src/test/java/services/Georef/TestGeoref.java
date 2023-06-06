package services.Georef;

import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

public class TestGeoref {
    @Test
    public void testProvincias() throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();

        for(Provincia unaProvincia: listadoDeProvinciasArgentinas.provincias){
            System.out.println(unaProvincia.nombre +" " + unaProvincia.id);
        }

        Optional<Provincia> posibleProvincia = listadoDeProvinciasArgentinas.provinciaDeId(6);

        Assertions.assertEquals((posibleProvincia.get().nombre), "Buenos Aires");
    }
    @Test
    public void testMunicipios() throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        ListadoDeMunicipios municipiosDeLaProvincia = servicioGeoref.listadoDeMunicipiosDeProvincia(servicioGeoref.listadoDeProvincias().provinciaDeId(2).get());
        for(Municipio unMunicipio: municipiosDeLaProvincia.municipios){
            System.out.println(unMunicipio.nombre + " " + unMunicipio.id);
        }
    }

    @Test
    public void testDepartamentos() throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        ListadoDeDepartamentos departamentosDeLaProvincia = servicioGeoref.listadoDeDepartamentosDeProvincia(servicioGeoref.listadoDeProvincias().provinciaDeId(2).get());
        for(Departamento unDepto: departamentosDeLaProvincia.departamentos){
            System.out.println(unDepto.nombre + " " + unDepto.id);
        }
    }
}
