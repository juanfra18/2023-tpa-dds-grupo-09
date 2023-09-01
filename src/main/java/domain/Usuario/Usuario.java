package domain.Usuario;

import domain.Seguridad.RegistroDeUsuarioException;
import domain.Seguridad.ValidadorDeContrasenias;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue
    private int id;

    @Column
    @Getter
    private String username;
    @Column
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

