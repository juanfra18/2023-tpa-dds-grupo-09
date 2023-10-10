package models.persistence;

import models.Config.Config;
import models.domain.Entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import models.domain.Personas.Comunidad;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.Rol;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.Repositorios.*;
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

            //PARA PROBAR LA PAGINA WEB

            RepositorioDeUsuarios repositorioDeUsuarios = RepositorioDeUsuarios.getInstancia();
            RepositorioRoles repositorioRoles = RepositorioRoles.getInstancia();
            Usuario u1 = new Usuario();
            Usuario u2 = new Usuario();
            u1.setUsername("messi10");
            u2.setUsername("kuni9");
            u1.cambiarContrasenia("LaCasaEnElLag@");
            u2.cambiarContrasenia("LaCasaEnElLag@");

            Rol admin = new Rol();
            admin.setNombre("administrador");
            admin.setTipo(TipoRol.ADMINISTRADOR);
            Rol basico = new Rol();
            basico.setNombre("basico");
            basico.setTipo(TipoRol.USUARIO_BASICO);

            repositorioRoles.agregar(admin);
            repositorioRoles.agregar(basico);

            u1.setRol(admin);
            u2.setRol(basico);

            repositorioDeUsuarios.agregar(u1);
            repositorioDeUsuarios.agregar(u2);

            RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
            MiembroDeComunidad mi = new MiembroDeComunidad();
            mi.setApellido("Messi");
            mi.setNombre("Leo");
            mi.setUsuario(u1);

            repositorioMiembroDeComunidad.agregar(mi);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}



