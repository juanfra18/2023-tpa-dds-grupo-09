package domain;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {
    private String username;
    private String contrasenia;

    private Seguridad seguridad;

    public Usuario(String username, String contrasenia) {
        this.username = username;
        this.contrasenia = contrasenia;
    }


    public boolean cambiarContrasnia(String nuevaContrasenia, Seguridad seguridad) throws IOException {
        boolean validarContrasenia = seguridad.validarContrasenia(nuevaContrasenia);
        boolean contraseniDistinta = nuevaContrasenia != contrasenia;
        return validarContrasenia && contraseniDistinta;
    }




}

