package models.persistence;

import models.domain.Entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import models.domain.Incidentes.Posicion;
import models.domain.Notificaciones.CuandoSuceden;
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


public class Seed implements WithSimplePersistenceUnit {
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

            Posicion posicion = new Posicion();
            posicion.setPosicion("-34.6078602,-58.383111");

            RepositorioPosicion.getInstancia().agregar(posicion);


            //PARA PROBAR LA PAGINA WEB

            RepositorioDeUsuarios repositorioDeUsuarios = RepositorioDeUsuarios.getInstancia();
            RepositorioRoles repositorioRoles = RepositorioRoles.getInstancia();
            Usuario u1 = new Usuario();
            Usuario u2 = new Usuario();
            Usuario u3 = new Usuario();
            u1.setUsername("messi10");
            u2.setUsername("kuni9");
            u3.setUsername("dibu23");
            u1.setContrasenia("LaCasaEnElLag@");
            u2.setContrasenia("LaCasaEnElLag@");
            u3.setContrasenia("LaCasaEnElLag@");

            Rol admin = new Rol();
            admin.setNombre("administrador");
            admin.setTipo(TipoRol.ADMINISTRADOR);
            Rol basico = new Rol();
            basico.setNombre("basico");
            basico.setTipo(TipoRol.USUARIO_BASICO);
            Rol empresa = new Rol();
            empresa.setNombre("empresa");
            empresa.setTipo(TipoRol.USUARIO_EMPRESA);

            repositorioRoles.agregar(admin);
            repositorioRoles.agregar(basico);
            repositorioRoles.agregar(empresa);

            u1.setRol(admin);
            u2.setRol(basico);
            u3.setRol(basico);

            repositorioDeUsuarios.agregar(u1);
            repositorioDeUsuarios.agregar(u2);
            repositorioDeUsuarios.agregar(u3);

            RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();
            MiembroDeComunidad mi = new MiembroDeComunidad();
            mi.setApellido("Messi");
            mi.setNombre("Leo");
            mi.setUsuario(u1);

            MiembroDeComunidad mi2 = new MiembroDeComunidad();
            mi2.setApellido("Kun");
            mi2.setNombre("Aguero");

            MiembroDeComunidad mi3 = new MiembroDeComunidad();
            mi3.setApellido("Emiliano");
            mi3.setNombre("Martinez");

            ReceptorDeNotificaciones receptorDeNotificaciones1 = new ReceptorDeNotificaciones();
            receptorDeNotificaciones1.setMail("diseniogrupo9@gmail.com");
            receptorDeNotificaciones1.setTelefono("01144444444");
            receptorDeNotificaciones1.cambiarFormaDeNotificar(new CuandoSuceden());
            receptorDeNotificaciones1.cambiarMedioDeComunicacion(new ViaMail());

            ReceptorDeNotificaciones receptorDeNotificaciones2 = new ReceptorDeNotificaciones();
            receptorDeNotificaciones2.setMail("diseniogrupo9@gmail.com");
            receptorDeNotificaciones2.setTelefono("01121212121");
            receptorDeNotificaciones2.cambiarFormaDeNotificar(new CuandoSuceden());
            receptorDeNotificaciones2.cambiarMedioDeComunicacion(new ViaMail());

            ReceptorDeNotificaciones receptorDeNotificaciones3 = new ReceptorDeNotificaciones();
            receptorDeNotificaciones3.setMail("diseniogrupo9@gmail.com");
            receptorDeNotificaciones3.setTelefono("01132432424");
            receptorDeNotificaciones3.cambiarFormaDeNotificar(new CuandoSuceden());
            receptorDeNotificaciones3.cambiarMedioDeComunicacion(new ViaMail());

            mi.setReceptorDeNotificaciones(receptorDeNotificaciones1);
            mi.setUsuario(u1);

            mi2.setReceptorDeNotificaciones(receptorDeNotificaciones2);
            mi2.setUsuario(u2);

            mi3.setReceptorDeNotificaciones(receptorDeNotificaciones3);
            mi3.setUsuario(u3);

            repositorioMiembroDeComunidad.agregar(mi);
            repositorioMiembroDeComunidad.agregar(mi2);
            repositorioMiembroDeComunidad.agregar(mi3);

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



