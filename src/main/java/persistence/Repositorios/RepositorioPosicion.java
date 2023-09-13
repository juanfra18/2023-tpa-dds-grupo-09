package persistence.Repositorios;

import domain.Incidentes.Posicion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioPosicion implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    private static RepositorioPosicion instancia = null;

    private RepositorioPosicion() {
        tx = entityManager().getTransaction();
    }

    public static  RepositorioPosicion getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioPosicion();
        }
        return instancia;
    }

    public static void main(String[] args) {
        RepositorioPosicion repo = new RepositorioPosicion();
        Posicion posicion = new Posicion();
        posicion.setPosicion("40.7486,-73.9864");

        repo.agregar(posicion);

        posicion.setPosicion("40.7973,-73.9864");

        repo.modificar(posicion);

        System.out.println(repo.buscar(posicion.getId()).distancia(posicion));

        System.out.println(repo.buscarTodos().size());

        //repo.eliminar(comunidad);
    }
    public void agregar(Posicion posicion) {
        this.tx.begin();
        entityManager().persist(posicion);
        this.tx.commit();
    }
    public void modificar(Posicion posicion) {
        this.tx.begin();
        entityManager().merge(posicion);
        this.tx.commit();
    }
    public void eliminar(Posicion posicion) {
        this.tx.begin();
        entityManager().remove(posicion);
        this.tx.commit();
    }
    public Posicion buscar(long id){
        return entityManager().find(Posicion.class, id);
    }
    public List<Posicion> buscarTodos(){
        return entityManager().createQuery("from Posicion", Posicion.class).getResultList();
    }
}