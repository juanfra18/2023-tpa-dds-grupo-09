package Repositorios;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEntidad;
import domain.Entidades.TipoEstablecimiento;
import domain.Notificaciones.CuandoSuceden;
import domain.Notificaciones.FormaDeNotificar;
import domain.Notificaciones.MedioDeComunicacion;
import domain.Notificaciones.ViaMail;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.AfterEach;
import persistence.Repositorios.RepositorioEntidad;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;

public class Minimain implements WithSimplePersistenceUnit {

    private MiembroDeComunidad miembro;
    private Municipio generalAlvarado;
    private Provincia buenosAires;
    private Servicio banioHombres;
    private Establecimiento estacionRetiro;
    private Entidad lineaMitre;
    private FormaDeNotificar cuandoSuceden = new CuandoSuceden();
    private MedioDeComunicacion mail = new ViaMail();
    private EntityTransaction tx;
    private RepositorioEntidad repositorioLineaMitre;

    @Before
    public void init() {
        tx = entityManager().getTransaction();
        tx.begin();

        miembro = new MiembroDeComunidad();
        miembro.setNombre("jose");
        miembro.setApellido("perez");
        miembro.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
        miembro.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
        miembro.getReceptorDeNotificaciones().setMail("perezjose@gmail.com");
        miembro.getReceptorDeNotificaciones().setTelefono("123456789");

        banioHombres = new Banio();
        banioHombres.setTipo("CABALLEROS");

        buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        buenosAires.setId(1);

        generalAlvarado = new Municipio();
        generalAlvarado.setProvincia(buenosAires);
        generalAlvarado.setNombre("General Alvarado");
        generalAlvarado.setId(2);

        estacionRetiro = new Establecimiento();
        estacionRetiro.setNombre("Retiro");
        estacionRetiro.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        estacionRetiro.setLocalizacion(generalAlvarado);
        estacionRetiro.agregarServicio(banioHombres);

        lineaMitre = new Entidad();
        lineaMitre.setNombre("Linea Mitre");
        lineaMitre.setTipoEntidad(TipoEntidad.FERROCARRIL);
        lineaMitre.agregarEstablecimiento(estacionRetiro);

        repositorioLineaMitre = RepositorioEntidad.getInstancia();
    }

    @Test
    public void cargaBasica() {
        try {
            repositorioLineaMitre.agregar(lineaMitre);
        } catch (Exception e) {

            e.printStackTrace();

            tx.rollback();
        }
    }

    @AfterEach
    public void finish() {
        tx.commit();
    }
}

