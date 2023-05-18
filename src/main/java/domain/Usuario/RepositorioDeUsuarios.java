package domain.Usuario;

import domain.Seguridad.RegistroDeUsuarioException;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDeUsuarios {
    private List<Usuario> usuarios;

    public RepositorioDeUsuarios(){
        usuarios = new ArrayList<>();
    }

    public void agregarUsername(Usuario usuario) throws RegistroDeUsuarioException {
        if (usuarios.stream().anyMatch(u -> (usuario.getUsername() == u.getUsername()))) {
            throw new RegistroDeUsuarioException("El nombre de usuario ya ha sido utilizado");
        }
        else {
            usuarios.add(usuario);
        }
    }

}
