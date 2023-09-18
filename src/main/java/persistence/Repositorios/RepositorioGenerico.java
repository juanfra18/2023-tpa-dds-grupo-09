package persistence.Repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class RepositorioGenerico<T> implements WithSimplePersistenceUnit {
    @PersistenceContext
    private Class<T> entityClass;
    public RepositorioGenerico(Class<T> entityClass) { this.entityClass = entityClass; }
    public void agregar(T entity) { entityManager().persist(entity); }
    public void eliminar(T entity) { entityManager().remove(entity); }

    public T buscar(long id) {
        return entityManager().find(entityClass, id);
    }

    public List<T> buscarTodos() { return entityManager().createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList(); }
}
