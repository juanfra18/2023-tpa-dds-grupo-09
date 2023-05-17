package domain.Usuario;

import domain.Seguridad.RegistroDeUsuarioException;
import domain.Seguridad.ValidadorDeContrasenias;
import lombok.Getter;


public class Usuario {
    @Getter
    private String username;
    private String contrasenia;
    private ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

  public Usuario(String username, String contrasenia) throws RegistroDeUsuarioException {
      validador.validarContrasenia(contrasenia);
      this.username = username;
      this.contrasenia = contrasenia;
    }
    public void cambiarContrasenia(String nuevaContrasenia) throws RegistroDeUsuarioException {
      if(nuevaContrasenia != contrasenia) {
          validador.validarContrasenia(nuevaContrasenia);
          this.contrasenia = nuevaContrasenia;
      }
    }
}

