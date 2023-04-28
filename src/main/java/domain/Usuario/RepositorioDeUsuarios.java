package domain.Usuario;

import domain.Seguridad.RegistroDeUsuarioException;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDeUsuarios {
    private List<String> usernames;

    public RepositorioDeUsuarios(){
        usernames = new ArrayList<>();
    }

    public void validarUsername(String username) throws RegistroDeUsuarioException {
        if (usernames.contains(username)) {
            throw new RegistroDeUsuarioException("El nombre de usuario ya ha sido utilizado");
        }
        else {
            usernames.add(username);
        }
    }

}
