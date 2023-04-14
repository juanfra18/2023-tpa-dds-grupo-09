package domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {
    private String username;
    private String contrasenia;

    public Usuario(String username, String contrasenia) {
        this.username = username;
        this.contrasenia = contrasenia;
    }
}

