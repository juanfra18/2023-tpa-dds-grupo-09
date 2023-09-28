package Usuario;

import models.domain.Seguridad.RegistroDeUsuarioException;
import models.persistence.Repositorios.RepositorioDeUsuarios;
import models.domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestUsuario {
    static RepositorioDeUsuarios repo;
    static Usuario usuario1;

    @BeforeAll
    public static void init1(){
        usuario1 = new Usuario();
        usuario1.setUsername("pedrito");
        usuario1.cambiarContrasenia("HolaMundo@3");
        repo = RepositorioDeUsuarios.getInstancia();
        repo.agregarUsername(usuario1);
    }

    @Test
    public void cambiarContrasenia() {
        String nuevaContrasenia = "MicAsa!*#";
        Assertions.assertDoesNotThrow(() -> {
            usuario1.cambiarContrasenia(nuevaContrasenia);
        });
    }
    @Test
    public void testRepositorioExito(){ //es exitoso porque logra almacenar 2 usernames ya que estos son distintos
        Usuario usuario2 = new Usuario();
        usuario2.setUsername("jorge");
        usuario2.cambiarContrasenia("HolaMundo@3");
        Assertions.assertDoesNotThrow(() -> {
            repo.agregarUsername(usuario2);
        });
    }
    @Test
    public void testRepositorioFalla() throws RegistroDeUsuarioException { //falla ya que ambos usernames son iguales
        Usuario usuario2 = new Usuario();
        usuario2.setUsername("pedrito" );
        usuario2.cambiarContrasenia("HolaMundo@3");
        Assertions.assertThrows(RegistroDeUsuarioException.class, () -> repo.agregarUsername(usuario2));
    }
}
