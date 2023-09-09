package domain.Persistencia.Repositorios;

import domain.Seguridad.RegistroDeUsuarioException;
import domain.Usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDeUsuarios implements WithSimplePersistenceUnit { // Ver como usar el agregar UsernameTODO
    private List<Usuario> usuarios;
    private EntityTransaction tx;
    public RepositorioDeUsuarios(){
        this.tx = entityManager().getTransaction();
        usuarios = new ArrayList<>();
    }
    public static void main(String[] args){

        RepositorioDeUsuarios repo=new RepositorioDeUsuarios();

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
    public void agregarUsername(Usuario usuario) throws RegistroDeUsuarioException {
        if (usuarios.stream().anyMatch(u -> (usuario.getUsername() == u.getUsername()))) {
            throw new RegistroDeUsuarioException("El nombre de usuario ya ha sido utilizado");
        }
        else {
            usuarios.add(usuario);
        }
    }
    public void agregar(Usuario usuario) {
        this.tx.begin();
        entityManager().persist(usuario);
        this.tx.commit();
    }
    public void modificar(Usuario usuario) {
        this.tx.begin();
        entityManager().merge(usuario);
        this.tx.commit();
    }
    public void eliminar(Usuario usuario) {
        this.tx.begin();
        entityManager().remove(usuario);
        this.tx.commit();
    }
    public Usuario buscar(long id){
        return entityManager().find(Usuario.class, id);
    }
    public List<Usuario> buscarTodos(){
        return entityManager().createQuery("from Usuario", Usuario.class).getResultList();
    }

}
