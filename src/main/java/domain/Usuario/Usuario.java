package domain.Usuario;

import java.io.IOException;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.Seguridad;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {
    private String username;
    private String contrasenia;
    private Seguridad seguridad ;

  public Usuario(String username, String contrasenia) throws IOException, InicioDeSesionException {
        this.username = username;
        this.contrasenia = contrasenia;
        seguridad = new Seguridad();
        seguridad.registrarUsuario(username,contrasenia);
    }
    public void cambiarContrasenia(String nuevaContrasenia) throws IOException, InicioDeSesionException {
      if(nuevaContrasenia != contrasenia)
      {
        seguridad.registrarUsuario(this.getUsername(),nuevaContrasenia);
        this.contrasenia = nuevaContrasenia;
      }
    }
}

