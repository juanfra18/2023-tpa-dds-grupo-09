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
import models.services.Localizacion.ListadoDeProvincias;

import javax.persistence.*;



public class Seed implements WithSimplePersistenceUnit {
    public static void main(String[] args) {
        seed();
    }
    public static void seed() {
        EntityManager em = EntityManagerSingleton.getInstance();
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
        RepositorioDeMunicipios repositorioDeMunicipios = RepositorioDeMunicipios.getInstancia();
        RepositorioProvincias repositorioProvincias = RepositorioProvincias.getInstancia();
        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();


        try {
            em.getTransaction().begin();

            em.createNativeQuery("DROP TABLE IF EXISTS rol_permiso").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS miembroDeComunidad_comunidad").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS miembroDeComunidad_entidad").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS miembroDeComunidad_municipio").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS miembroDeComunidad_provincia").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS reporteDeIncidente").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS parServicioRol").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS miembroDeComunidad").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS usuario").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS rol").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS comunidad").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS incidente").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS servicio").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS establecimiento").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS entidad").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS entidadPrestadora").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS organismoDeControl").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS municipio").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS permiso").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS posicion").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS provincia").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS receptorDeNotificaciones").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS hibernate_sequence").executeUpdate();

            em.getTransaction().commit();
            em.close();

            em = EntityManagerSingleton.getInstance();
            em.getTransaction().begin();

            //Se cargan las provincias
            listadoDeProvinciasArgentinas.getProvincias().forEach(provincia -> repositorioProvincias.agregar(provincia));

            //Se cargan los municipios
            repositorioProvincias.buscarTodos().forEach(
                    provincia -> servicioGeoref.listadoDeMunicipiosDeProvincia(provincia).
                            getMunicipios().forEach(municipio -> repositorioDeMunicipios.agregar(municipio)));

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
            u1.setContrasenia("Grupodisenio@");
            u2.setContrasenia("Grupodisenio@");
            u3.setContrasenia("Grupodisenio@");

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
            em.close();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}



