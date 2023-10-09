package models.persistence;

import models.Config.Config;
import models.domain.Entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import models.domain.Personas.Comunidad;
import models.persistence.Repositorios.RepositorioComunidad;
import models.persistence.Repositorios.RepositorioDeMunicipios;
import models.persistence.Repositorios.RepositorioDeOrganismosDeControl;
import models.persistence.Repositorios.RepositorioProvincias;
import models.services.APIs.Georef.ServicioGeoref;
import models.services.Archivos.CargadorDeDatos;
import models.services.Archivos.SistemaDeArchivos;
import models.services.Localizacion.ListadoDeProvincias;

import javax.persistence.*;
import java.util.List;


public class Minimain implements WithSimplePersistenceUnit {
    public static void main(String[] args) {
        EntityManager em = EntityManagerSingleton.getInstance();
        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
        RepositorioDeMunicipios repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
        RepositorioProvincias repositorioProvincias = RepositorioProvincias.getInstancia();
        RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
        List<OrganismoDeControl> empresas;
        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();

        RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();

        try {
            em.getTransaction().begin();

            //Se cargan las provincias
            listadoDeProvinciasArgentinas.getProvincias().forEach(provincia -> repositorioProvincias.agregar(provincia));

            //Se cargan los municipios
            repositorioProvincias.buscarTodos().forEach(
                provincia -> servicioGeoref.listadoDeMunicipiosDeProvincia(provincia).
                    getMunicipios().forEach(municipio -> repositorioDeMunicipios.agregar(municipio)));

            //Se cargan las empresas
            empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);
            empresas.forEach(e -> repositorioDeOrganismosDeControl.agregar(e));
            Comunidad c1 = new Comunidad();
            c1.setNombre("CASAAA");
            c1.setGradosDeConfianza("12");
            Comunidad c2 = new Comunidad();
            c2.setNombre("CASAAasdasdasdA");
            c2.setGradosDeConfianza("13");
            repositorioComunidad.agregar(c1);
            repositorioComunidad.agregar(c2);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}



