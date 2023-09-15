package persistence.Repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class RepositorioGenerico<T> implements WithSimplePersistenceUnit {
    @PersistenceContext
    private Class<T> entityClass;
    private EntityTransaction tx;
    public RepositorioGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
        tx = entityManager().getTransaction();
    }
    public void agregar(T entity) {
        tx.begin();
        entityManager().persist(entity);
        tx.commit();
    }
    public void eliminar(T entity) {
        tx.begin();
        entityManager().remove(entity);
        tx.commit();
    }

    public T buscar(long id) {
        return entityManager().find(entityClass, id);
    }

    public List<T> buscarTodos() {
        return entityManager().createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }
}
