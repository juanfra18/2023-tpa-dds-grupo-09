package domain.Persistencia.Repositorios;

import domain.Entidades.*;
import domain.Notificaciones.*;
import domain.Personas.Comunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeOrganismosDeControl implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    public RepositorioDeOrganismosDeControl(){
        this.tx = entityManager().getTransaction();
    }

    public static void main(String[] args) {
        RepositorioDeOrganismosDeControl repo = new RepositorioDeOrganismosDeControl();

        Provincia jujuy = new Provincia();
        jujuy.setId(38);
        jujuy.setNombre("Jujuy");
        Municipio Yavi = new Municipio();
        Yavi.setId(386273);
        Yavi.setNombre("Yavi");
        Yavi.setProvincia(jujuy);

        Servicio banio = new Banio();
        banio.setTipo("DAMAS");

        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setLocalizacion(Yavi);
        establecimiento.setNombre("Yavi");
        establecimiento.setTipoEstablecimiento(TipoEstablecimiento.valueOf("ESTACION"));
        establecimiento.agregarServicio(banio);

        Entidad entidad = new Entidad();
        entidad.setTipoEntidad(TipoEntidad.valueOf("FERROCARRIL"));
        entidad.setNombre("Mitre");
        entidad.agregarEstablecimiento(establecimiento);

        EntidadPrestadora entidadPrestadora = new EntidadPrestadora();
        entidadPrestadora.setNombre("Diegomaster");
        entidadPrestadora.setPersonaMail("Rizzler@callme.com");
        entidadPrestadora.agregarEntidad(entidad);

        OrganismoDeControl organismoDeControl = new OrganismoDeControl();
        organismoDeControl.setNombre("Paolopoli");
        organismoDeControl.setPersonaMail("jfranpali@mujereslover");
        organismoDeControl.agregarEntidadPrestadora(entidadPrestadora);

        repo.agregar(organismoDeControl);

        organismoDeControl.setNombre("Xx__messilovers__xX");

        repo.modificar(organismoDeControl);

        System.out.println(repo.buscar(organismoDeControl.getId()).getNombre());

        System.out.println(repo.buscarTodos().size());

        //repo.eliminar(comunidad);
    }
    public void agregar(OrganismoDeControl organismoDeControl) {
        this.tx.begin();
        entityManager().persist(organismoDeControl);
        this.tx.commit();
    }
    public void modificar(OrganismoDeControl organismoDeControl) {
        this.tx.begin();
        entityManager().merge(organismoDeControl);
        this.tx.commit();
    }
    public void eliminar(OrganismoDeControl organismoDeControl) {
        this.tx.begin();
        entityManager().remove(organismoDeControl);
        this.tx.commit();
    }
    public OrganismoDeControl buscar(long id){
        return entityManager().find(OrganismoDeControl.class, id);
    }
    public List<OrganismoDeControl> buscarTodos(){
        return entityManager().createQuery("from OrganismoDeControl", OrganismoDeControl.class).getResultList();
    }
}
