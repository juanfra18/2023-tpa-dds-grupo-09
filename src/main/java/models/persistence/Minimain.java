package models.persistence;

import models.Config.Config;
import models.domain.Entidades.Entidad;
import models.domain.Entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import models.domain.Notificaciones.CuandoSuceden;
import models.domain.Notificaciones.FormaDeNotificar;
import models.domain.Notificaciones.ReceptorDeNotificaciones;
import models.domain.Notificaciones.ViaMail;
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
            //empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);
            //empresas.forEach(e -> repositorioDeOrganismosDeControl.agregar(e));

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
          //  mi.agregarEntidadDeInteres(repositorioDeOrganismosDeControl.buscarTodos().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(0));

            MiembroDeComunidad mi2 = new MiembroDeComunidad();
            mi2.setApellido("Kun");
            mi2.setNombre("Aguero");

            ReceptorDeNotificaciones receptorDeNotificaciones = new ReceptorDeNotificaciones();
            receptorDeNotificaciones.setMail("juanpaoli@gmail.com");
            receptorDeNotificaciones.setTelefono("01144444444");
            receptorDeNotificaciones.cambiarFormaDeNotificar(new CuandoSuceden());
            receptorDeNotificaciones.cambiarMedioDeComunicacion(new ViaMail());

            mi2.setReceptorDeNotificaciones(receptorDeNotificaciones);
            mi2.setUsuario(u2);
           // mi2.agregarEntidadDeInteres(repositorioDeOrganismosDeControl.buscarTodos().get(0).getEntidadesPrestadoras().get(0).getEntidades().get(1));

            repositorioMiembroDeComunidad.agregar(mi);
            repositorioMiembroDeComunidad.agregar(mi2);

            Comunidad c1 = new Comunidad();
            c1.setNombre("messistas");

            Comunidad c2 = new Comunidad();
            c2.setNombre("seleccionArg");

            RepositorioComunidad repositorioComunidad = RepositorioComunidad.getInstancia();
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



