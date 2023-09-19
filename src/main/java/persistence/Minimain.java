package persistence;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEntidad;
import domain.Entidades.TipoEstablecimiento;
import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import persistence.Repositorios.RepositorioDeMunicipios;
import persistence.Repositorios.RepositorioEntidad;
import persistence.Repositorios.RepositorioMiembroDeComunidad;
import persistence.Repositorios.RepositorioProvincias;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class Minimain implements WithSimplePersistenceUnit {
    public static void main(String[] args)  {
        MiembroDeComunidad miembro;
        Municipio generalAlvarado;
        Provincia buenosAires;
        Servicio banioHombres;
        Establecimiento estacionRetiro;
        Entidad lineaMitre;
        FormaDeNotificar cuandoSuceden = new CuandoSuceden();
        MedioDeComunicacion mail = new ViaMail();
        RepositorioEntidad repositorioLineaMitre;
        RepositorioDeMunicipios repositorioDeMunicipios;
        RepositorioProvincias repositorioProvincias;
        RepositorioMiembroDeComunidad repositorioMiembroDeComunidad;
        EntityTransaction tx;

        EntityManager em =
        tx = entityManager().getTransaction();

        tx.begin();

try {
        miembro = new MiembroDeComunidad();
        miembro.setNombre("jose");
        miembro.setApellido("perez");
        miembro.setReceptorDeNotificaciones(new ReceptorDeNotificaciones());
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
        repositorioProvincias = RepositorioProvincias.getInstancia();
        repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
        repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();


        repositorioProvincias.agregar(buenosAires);
        repositorioDeMunicipios.agregar(generalAlvarado);
        repositorioLineaMitre.agregar(lineaMitre);
        repositorioMiembroDeComunidad.agregar(miembro);

        tx.commit();
        } catch (Exception e) {

            e.printStackTrace();

            tx.rollback();

        }
    }
}

