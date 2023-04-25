package domain.Usuario;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.Seguridad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorDeUsuarios {
    private Seguridad seguridad;
    private List<Usuario> usuarios;

    public GestorDeUsuarios() throws FileNotFoundException {
        seguridad = new Seguridad();
        usuarios = new ArrayList<>();
    }

    public void crearUsuario(String username, String contrasenia) throws IOException, InicioDeSesionException {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername() == username) { throw new InicioDeSesionException("El usuario ya existe"); }
        }
        usuarios.add(new Usuario(seguridad, username, contrasenia));
    }

    public void cambiarContraseniaUsuario(String username, String nuevaContrasenia) throws IOException, InicioDeSesionException {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.getUsername() == username) {
                usuario.cambiarContrasenia(seguridad, nuevaContrasenia);
                usuarios.set(i, usuario);
            }
        }

    }

}
