package domain.Persistencia.Repositorios;


import domain.Usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeUsuario implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    public RepositorioDeUsuario(){
        this.tx = entityManager().getTransaction();
    }
    public static void main(String[] args){

      RepositorioDeUsuario repo=new RepositorioDeUsuario();

      Usuario jorge= new Usuario();
      jorge.setUsername("jorge");
      jorge.cambiarContrasenia("HolaMundo@3");

      repo.agregar(jorge);

      System.out.println(repo.buscar(jorge.getId()).getUsername());

      jorge.setUsername("yanosoyjorge:(");

      repo.modificar(jorge);

      System.out.println(repo.buscar(jorge.getId()).getUsername());

      repo.eliminar(jorge);
    }

    private void agregar(Usuario usuario) {
        this.tx.begin();
        entityManager().persist(usuario);
        this.tx.commit();
    }
    private void modificar(Usuario usuario) {
        this.tx.begin();
        entityManager().merge(usuario);
        this.tx.commit();
    }
    private void eliminar(Usuario usuario) {
        this.tx.begin();
        entityManager().remove(usuario);
        this.tx.commit();
    }
    private Usuario buscar(long id){
        return entityManager().find(Usuario.class, id);
    }
    private List<Usuario> buscarTodos(){
        return entityManager().createQuery("from MiembroDeComunidad", Usuario.class).getResultList();
    }
}
