package Usuario;

import domain.Seguridad.RegistroDeUsuarioException;
import domain.Seguridad.ValidadorDeContrasenias;
import domain.Usuario.RepositorioDeUsuarios;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUsuario {

    Usuario usuario;
    @Test
    public void cambiarContrasenia() {
        Assertions.assertDoesNotThrow(() -> usuario = new Usuario("pedro", "HolaMundo@3"));
        String nuevaContrasenia = "MicAsa!*#";
        Assertions.assertDoesNotThrow(() -> {
            usuario.cambiarContrasenia(nuevaContrasenia);
        });
    }

    @Test
    public void testRepositorioExito() throws RegistroDeUsuarioException {
        RepositorioDeUsuarios repo = new RepositorioDeUsuarios();
        Usuario usuario1 = new Usuario("pedrito", "HolaMundo@3");
        repo.validarUsername(usuario1);
        Usuario usuario2 = new Usuario("jorge", "HolaMundo@3");
        Assertions.assertDoesNotThrow(() -> repo.validarUsername(usuario2));
    }
    @Test
    public void testRepositorioFalla() throws RegistroDeUsuarioException {
        RepositorioDeUsuarios repo = new RepositorioDeUsuarios();
        Usuario usuario1 = new Usuario("pedrito", "HolaMundo@3");
        repo.validarUsername(usuario1);
        Usuario usuario2 = new Usuario("pedrito", "HolaMundo@3");
        Assertions.assertThrows(RegistroDeUsuarioException.class, () -> repo.validarUsername(usuario2));
    }
}
