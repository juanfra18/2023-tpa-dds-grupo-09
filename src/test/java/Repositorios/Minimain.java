package Repositorios;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEntidad;
import domain.Entidades.TipoEstablecimiento;
import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import persistence.Repositorios.RepositorioDeMunicipios;
import persistence.Repositorios.RepositorioEntidad;
import persistence.Repositorios.RepositorioMiembroDeComunidad;
import persistence.Repositorios.RepositorioProvincias;
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
    private RepositorioDeMunicipios repositorioDeMunicipios;
    private RepositorioProvincias repositorioProvincias;
    private RepositorioMiembroDeComunidad repositorioMiembroDeComunidad;

    @Before
    public void init() {
        this.tx = entityManager().getTransaction();
        this.tx.begin();

        this.miembro = new MiembroDeComunidad();
        this.miembro.setNombre("jose");
        this.miembro.setApellido("perez");
        this.miembro.setReceptorDeNotificaciones(new ReceptorDeNotificaciones());
        this.miembro.getReceptorDeNotificaciones().cambiarFormaDeNotificar(cuandoSuceden);
        this.miembro.getReceptorDeNotificaciones().cambiarMedioDeComunicacion(mail);
        this.miembro.getReceptorDeNotificaciones().setMail("perezjose@gmail.com");
        this.miembro.getReceptorDeNotificaciones().setTelefono("123456789");

        this.banioHombres = new Banio();
        this.banioHombres.setTipo("CABALLEROS");

        this.buenosAires = new Provincia();
        this.buenosAires.setNombre("Buenos Aires");
        this.buenosAires.setId(1);

        this.generalAlvarado = new Municipio();
        this.generalAlvarado.setProvincia(buenosAires);
        this.generalAlvarado.setNombre("General Alvarado");
        this.generalAlvarado.setId(2);

        this.estacionRetiro = new Establecimiento();
        this.estacionRetiro.setNombre("Retiro");
        this.estacionRetiro.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        this.estacionRetiro.setLocalizacion(generalAlvarado);
        this.estacionRetiro.agregarServicio(banioHombres);

        this.lineaMitre = new Entidad();
        this.lineaMitre.setNombre("Linea Mitre");
        this.lineaMitre.setTipoEntidad(TipoEntidad.FERROCARRIL);
        this.lineaMitre.agregarEstablecimiento(estacionRetiro);

        this.repositorioLineaMitre = RepositorioEntidad.getInstancia();
        this.repositorioProvincias = RepositorioProvincias.getInstancia();
        this.repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
        this.repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
    }

    @Test
    public void cargaBasica() {
        try {
            this.repositorioProvincias.agregar(buenosAires);
            this.repositorioDeMunicipios.agregar(generalAlvarado);
            this.repositorioLineaMitre.agregar(lineaMitre);
            this.repositorioMiembroDeComunidad.agregar(miembro);
        } catch (Exception e) {

            e.printStackTrace();

            this.tx.rollback();
        }
    }

    @After
    public void finish() {
        this.tx.commit();
    }
}

