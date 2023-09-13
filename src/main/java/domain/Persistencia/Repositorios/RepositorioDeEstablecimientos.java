package domain.Persistencia.Repositorios;

import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEstablecimiento;
import domain.Notificaciones.*;
import domain.Personas.Comunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeEstablecimientos implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    private static RepositorioDeEstablecimientos instancia = null;

    private RepositorioDeEstablecimientos() {
        tx = entityManager().getTransaction();
    }
    public static  RepositorioDeEstablecimientos getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioDeEstablecimientos();
        }
        return instancia;
    }
    public static void main(String[] args) {
        RepositorioDeEstablecimientos repo = new RepositorioDeEstablecimientos();
        Establecimiento estacionYavi = new Establecimiento();

        Provincia jujuy = new Provincia();
        jujuy.setId(38);
        jujuy.setNombre("Jujuy");
        Municipio Yavi = new Municipio();
        Yavi.setId(386273);
        Yavi.setNombre("Yavi");
        Yavi.setProvincia(jujuy);

        Servicio banio = new Banio();
        banio.setTipo("DAMAS");

        estacionYavi.setLocalizacion(Yavi);
        estacionYavi.setNombre("Yavi");
        estacionYavi.setTipoEstablecimiento(TipoEstablecimiento.valueOf("ESTACION"));
        estacionYavi.agregarServicio(banio);

        repo.agregar(estacionYavi);

        estacionYavi.setNombre("Xx__messilovers__xX");

        repo.modificar(estacionYavi);

        System.out.println(repo.buscar(estacionYavi.getId()).getNombre());

        System.out.println(repo.buscarTodos().size());

        //repo.eliminar(comunidad);

        Establecimiento estacionYavi2 = new Establecimiento();
        estacionYavi2.setNombre("Estacion");
        estacionYavi2.setLocalizacion(Yavi);
        estacionYavi2.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);

        repo.agregar(estacionYavi2);
    }
    public void agregar(Establecimiento establecimiento) {
        this.tx.begin();
        entityManager().persist(establecimiento);
        this.tx.commit();
    }
    public void modificar(Establecimiento establecimiento) {
        this.tx.begin();
        entityManager().merge(establecimiento);
        this.tx.commit();
    }
    public void eliminar(Establecimiento establecimiento) {
        this.tx.begin();
        entityManager().remove(establecimiento);
        this.tx.commit();
    }
    public Establecimiento buscar(long id){
        return entityManager().find(Establecimiento.class, id);
    }
    public List<Establecimiento> buscarTodos(){
        return entityManager().createQuery("from Establecimiento", Establecimiento.class).getResultList();
    }
}
