package Usuario;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.Seguridad;
import domain.Usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestUsuario {

    Usuario usuario;
    Seguridad seguridad;

    @Test
    public void cambiarContrasenia() throws InicioDeSesionException, IOException {
        Usuario usuario = new Usuario("juan", "HolaMundo@3");
        String nuevaContrasenia = "MicAsa!*#";
        Assertions.assertDoesNotThrow(() -> {
            usuario.cambiarContrasenia(nuevaContrasenia);
        });
    }
}
