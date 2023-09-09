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

public class RepositorioDeEntidadPrestadora implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    public RepositorioDeEntidadPrestadora(){
        this.tx = entityManager().getTransaction();
    }

    public static void main(String[] args) {
        RepositorioDeEntidadPrestadora repo = new RepositorioDeEntidadPrestadora();
        EntidadPrestadora entidadPrestadora = new EntidadPrestadora();

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

        entidadPrestadora.setNombre("Diegomaster");
        entidadPrestadora.setPersonaMail("Rizzler@callme.com");
        entidadPrestadora.agregarEntidad(entidad);


        repo.agregar(entidadPrestadora);

        entidadPrestadora.setNombre("Xx__messilovers__xX");

        repo.modificar(entidadPrestadora);

        System.out.println(repo.buscar(entidadPrestadora.getId()).getNombre());

        System.out.println(repo.buscarTodos().size());

        //repo.eliminar(comunidad);
    }
    public void agregar(EntidadPrestadora entidadPrestadora) {
        this.tx.begin();
        entityManager().persist(entidadPrestadora);
        this.tx.commit();
    }
    public void modificar(EntidadPrestadora entidadPrestadora) {
        this.tx.begin();
        entityManager().merge(entidadPrestadora);
        this.tx.commit();
    }
    public void eliminar(EntidadPrestadora entidadPrestadora) {
        this.tx.begin();
        entityManager().remove(entidadPrestadora);
        this.tx.commit();
    }
    public EntidadPrestadora buscar(long id){
        return entityManager().find(EntidadPrestadora.class, id);
    }
    public List<EntidadPrestadora> buscarTodos(){
        return entityManager().createQuery("from EntidadPrestadora", EntidadPrestadora.class).getResultList();
    }
}
