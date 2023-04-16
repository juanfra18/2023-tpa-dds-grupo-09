package domain;

import java.io.IOException;
import java.util.Scanner;

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
        seguridad.registrarse(username,contrasenia);
    }
    public void cambiarContrasenia(String nuevaContrasenia) throws IOException {
      if(nuevaContrasenia != contrasenia)
      {
        seguridad.validarContrasenia(this.getUsername(),nuevaContrasenia);
        this.contrasenia = nuevaContrasenia;
      }
    }

  public static void main(String[] args) throws InicioDeSesionException, IOException { //para probar codigo
    Usuario juan = new Usuario("l10N3lMess!","l10N3lMess!");
    Usuario tomas = new Usuario("tomas","1234");
  }
}

