package domain.Usuario;

import domain.Persistencia.Persistente;
import domain.Seguridad.RegistroDeUsuarioException;
import domain.Seguridad.ValidadorDeContrasenias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario extends Persistente {
    @Column(name = "username")
    @Getter
    @Setter
    private String username;
    @Column(name = "password")
    private String contrasenia;
    @Transient
    private ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
  public Usuario() throws RegistroDeUsuarioException {
      this.validador = new ValidadorDeContrasenias();
    }
    public void cambiarContrasenia(String nuevaContrasenia) throws RegistroDeUsuarioException {
      if(nuevaContrasenia != contrasenia) {
          validador.validarContrasenia(nuevaContrasenia);
          this.contrasenia = nuevaContrasenia;
      }
    }
}

