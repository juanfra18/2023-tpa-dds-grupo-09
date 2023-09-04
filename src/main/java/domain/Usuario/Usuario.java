package domain.Usuario;

import domain.Persistencia.Persistente;
import domain.Seguridad.RegistroDeUsuarioException;
import domain.Seguridad.ValidadorDeContrasenias;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario extends Persistente {
    @Column(name = "username")
    @Getter
    private String username;
    @Column(name = "password")
    private String contrasenia;
    @Transient
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

