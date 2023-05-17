package Servicios.Georef;

import Servicios.APIs.Georef.ServicioGeoref;
import Servicios.Localizacion.ListadoDeMunicipios;
import Servicios.Localizacion.ListadoDeProvincias;
import Servicios.Localizacion.Municipio;
import Servicios.Localizacion.Provincia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

public class TestListaProvincias {
    @Test
    public void testProvincias() throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();

        Optional<Provincia> posibleProvincia = listadoDeProvinciasArgentinas.provinciaDeId(6);

        Assertions.assertEquals((posibleProvincia.get().nombre), "Buenos Aires");
    }
    @Test
    public void testMunicipios() throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        ListadoDeMunicipios municipiosDeLaProvincia = servicioGeoref.listadoDeMunicipiosDeProvincia(servicioGeoref.listadoDeProvincias().provinciaDeId(6).get());
        for(Municipio unMunicipio: municipiosDeLaProvincia.municipios){
            System.out.println(unMunicipio.nombre + unMunicipio.id);
        }
    }
}
