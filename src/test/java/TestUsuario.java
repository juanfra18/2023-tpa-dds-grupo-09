import domain.Seguridad;
import domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
public class TestUsuario {

    Usuario usuario;
    Seguridad seguridad;

    @BeforeEach
    public void init() throws FileNotFoundException {
        usuario = new Usuario();
    }
    @Test
    public void cambiarContrasenia() throws IOException {
        Usuario usuario = new Usuario("juan", "HolaMundo@3");
        String nuevaContrasenia = "HolaMundo@3";
        Assertions.assertFalse(usuario.cambiarContrasnia(nuevaContrasenia, seguridad), "La contraseña no debe ser igual a la anteriror");
    }

}
