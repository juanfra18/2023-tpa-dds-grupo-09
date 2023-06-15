package Personas;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Personas.Comunidad;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Elevacion;
import domain.Servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;
import services.Localizacion.Municipio;

import java.io.IOException;


public class TestInteresesMiembro {

  static MiembroDeComunidad miembro;
  static AdapterServicioGeo servicioGeo;
  static Municipio municipio;

  static Servicio banioHombres;
  static Servicio banioMujeres;
  static Establecimiento estacionRetiro;
  static Establecimiento estacionTolosa;
  static Entidad lineaMitre;
  static Entidad lineaRoca;

  @BeforeAll
  public static void init() throws IOException {
    miembro = new MiembroDeComunidad("perez", "jose", "perezjose@gmail.com");
    servicioGeo = ServicioGeoref.instancia();
    municipio = servicioGeo.obtenerMunicipio("General Alvarado");

    miembro.agregarMunicipio(municipio);

    banioHombres = new Banio("CABALLEROS");
    banioMujeres = new Banio("DAMAS");

    miembro.agregarServicioDeInteres(banioHombres);

    estacionRetiro = new Establecimiento("Retiro","ESTACION",municipio);
    estacionRetiro.agregarServicio(banioHombres);

    lineaMitre = new Entidad("Linea Mitre","FERROCARRIL");
    lineaMitre.agregarEstablecimiento(estacionRetiro);

    estacionTolosa = new Establecimiento("Tolosa","ESTACION",municipio);
    estacionTolosa.agregarServicio(banioMujeres);

    lineaRoca = new Entidad("Linea Roca","FERROCARRIL");
    lineaRoca.agregarEstablecimiento(estacionTolosa);
  }
  @Test
  public void leInteresaElServicioYElEstablecimiento(){
    miembro.agregarEntidadDeInteres(lineaMitre);
    Assertions.assertTrue(miembro.tieneInteres(banioHombres,estacionRetiro));
  }
  @Test
  public void noLeInteresaElServicio(){
    miembro.agregarEntidadDeInteres(lineaRoca);
    Assertions.assertFalse(miembro.tieneInteres(banioMujeres,estacionTolosa));
  }
  @Test
  public void leInteresaElServicioPeroNoElEstablecimiento(){
    Assertions.assertFalse(miembro.tieneInteres(banioHombres,estacionRetiro));
  }
}


