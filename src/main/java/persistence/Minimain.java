package persistence;

import Config.Config;
import domain.Entidades.*;
import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import io.github.flbulgarelli.jpa.extras.test.PersistenceTest;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import persistence.Repositorios.*;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import services.Localizacion.ListadoDeMunicipios;
import services.Localizacion.ListadoDeProvincias;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class Minimain implements WithSimplePersistenceUnit {
    public static void main(String[] args) {

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        RepositorioProvincias repositorioProvincias = RepositorioProvincias.getInstancia();
        RepositorioDeMunicipios repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
        RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
        ListadoDeProvincias listadoDeProvincias;
        List<OrganismoDeControl> empresas;


        //Se cargan las provincias y municipios
        listadoDeProvincias = servicioGeoref.listadoDeProvincias();

        //Se cargan las empresas
        empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);


        try {
            tx.begin();
            listadoDeProvincias.getProvincias().forEach(provincia -> repositorioProvincias.agregar(provincia));
            tx.commit();
        }
     catch (Exception e) {
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        e.printStackTrace();
    } finally {
            em.close();
        }
    }

    public static EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        return emf.createEntityManager();
    }
}



